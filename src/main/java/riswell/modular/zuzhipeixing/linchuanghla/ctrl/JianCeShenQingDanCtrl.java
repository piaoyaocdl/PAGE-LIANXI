package riswell.modular.zuzhipeixing.linchuanghla.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import lombok.SneakyThrows;
import riswell.modular.sysm.dao.WenJianDao;
import riswell.modular.sysm.domain.WenJian;
import riswell.modular.zuzhipeixing.linchuanghla.dao.JianCeShenQingDanDao;
import riswell.modular.zuzhipeixing.linchuanghla.domain.JianCeShenQingDan;
import riswell.util.GongJu;
import riswell.util.BaoBiaoGongJu;
import riswell.util.BaoBiaoGongJu.TiHuanType;

@Controller
@RequestMapping("/zuzhipeixing/linchuanghla")
public class JianCeShenQingDanCtrl
{
	@Autowired
	JianCeShenQingDanDao jianceshengqingdandao;

	@Autowired
	WenJianDao wenjiandao;

	/**
	 * 查询检测申请单
	 * 
	 * @param huanzhexingming
	 * @return
	 */
	@RequestMapping("/chaxunshenqingdan")
	@ResponseBody
	@Transactional
	public String chaxunshenqingdan(String huanzhexingming)
	{
		List<JianCeShenQingDan> re = jianceshengqingdandao.findAll(new Specification<JianCeShenQingDan>()
		{

			@Override
			public Predicate toPredicate(Root<JianCeShenQingDan> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{

				Predicate re = null;
				Predicate ls = null;
				ls = cb.greaterThan(root.get("id").as(Long.class), 0l);
				re = cb.and(ls);

				if (huanzhexingming != null && !huanzhexingming.equals(""))
				{
					ls = cb.equal(root.get("huanzhexingming").as(String.class), huanzhexingming);
					re = cb.and(re, ls);
				}

				return query.where(re).getRestriction();
			}
		});
		return JSON.toJSONString(re, true);

	}

	/**
	 * 添加申请单
	 * 
	 * @param jianCeShenQingDan
	 * @param wenjian
	 * @return
	 */
	@RequestMapping("/tianjiashenqingdan")
	@ResponseBody
	@Transactional
	public String tianjiashenqingdan(JianCeShenQingDan jianCeShenQingDan, String[] wenjian)
	{
		Map<String, String> re = new HashMap<>(0);

		Set<WenJian> wenjians = new HashSet<>();
		if (wenjian != null && wenjian.length > 0)
		{
			for (int i = 0; i < wenjian.length; i++)
			{
				WenJian ls = new WenJian();
				ls.setYuanwenjianming(wenjian[i]);
				ls.setXinwenjianming(WenJian.xinwenjianmingMaker(ls.getYuanwenjianming()));
				ls.setChuangjianshijian(new Date());
				re.put(ls.getYuanwenjianming(), ls.getXinwenjianming());
				wenjiandao.save(ls);
				wenjians.add(ls);
			}
		}
		jianCeShenQingDan.setFujian(wenjians);
		jianceshengqingdandao.save(jianCeShenQingDan);
		return JSON.toJSONString(re);
	}

	/**
	 * 下载报表
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("/xiazaibaobiao")
	@ResponseBody
	@SneakyThrows
	public ResponseEntity<byte[]> xiazaibaobiao(String json)
	{
		List<JianCeShenQingDan> jc = jianceshengqingdandao.findAll();

		Document doc = BaoBiaoGongJu.getDocument("shiyan.xml");
		Element root = doc.getRootElement();
		Element ele = BaoBiaoGongJu.getElement(root, "tihuan", TiHuanType.ID);
		Element pa = ele.getParent();

		for (int i = 0; i < jc.size(); i++)
		{
			JianCeShenQingDan ls = jc.get(i);
			Element e;
			Element cl = (Element) ele.clone();
			String pt;

			e = BaoBiaoGongJu.getElement(cl, "aaaa", TiHuanType.TEXT);
			if (ls.getHuanzhexingming() == null)
			{
				pt = "";
			} else
			{
				pt = ls.getHuanzhexingming();
			}
			e.setText(pt);

			e = BaoBiaoGongJu.getElement(cl, "bbbb", TiHuanType.TEXT);
			if (ls.getXingbie() == null)
			{
				pt = "";
			} else
			{
				pt = ls.getXingbie();
			}
			e.setText(pt);

			e = BaoBiaoGongJu.getElement(cl, "cccc", TiHuanType.TEXT);
			if (ls.getChushengriqi() == null)
			{
				pt = "";
			} else
			{
				pt = ls.getChushengriqi().toString();
			}
			e.setText(pt);

			pa.add(cl);

		}
		pa.remove(ele);
		String wjm = Double.toString(Math.random()).substring(2) + Long.toString(System.currentTimeMillis()) + ".doc";
		BaoBiaoGongJu.xieXML(GongJu.getXiangMuLuJing() + "临时文件/" + wjm, doc);

		ResponseEntity<byte[]> ls = GongJu.xiazai(wjm, "临时文件", "");
		return ls;
	}
}
