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
import riswell.util.XMLGongJu;
import riswell.util.XMLGongJu.XMLType;

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

		Document doc = XMLGongJu.getDocument(GongJu.getXiangMuLuJing() + "报表模版/shiyan.xml");
		Element root = doc.getRootElement();
		Element ele = XMLGongJu.getElement(root, "tihuan", XMLType.ID);
		Element pa = ele.getParent();

		for (int i = 0; i < jc.size(); i++)
		{
			JianCeShenQingDan ls = jc.get(i);
			Element e;
			Element cl = (Element) ele.clone();
			String pt;

			e = XMLGongJu.getElement(cl, "aaaa", XMLType.TEXT);
			if (ls.getHuanzhexingming() == null)
			{
				pt = "";
			} else
			{
				pt = ls.getHuanzhexingming();
			}
			e.setText(pt);

			e = XMLGongJu.getElement(cl, "bbbb", XMLType.TEXT);
			if (ls.getXingbie() == null)
			{
				pt = "";
			} else
			{
				pt = ls.getXingbie();
			}
			e.setText(pt);

			e = XMLGongJu.getElement(cl, "cccc", XMLType.TEXT);
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
		XMLGongJu.xieXML(GongJu.getXiangMuLuJing() + "临时文件/shishi.doc", doc);

		ResponseEntity<byte[]> ls = GongJu.xiazai("shishi.doc", "临时文件", "");
		return ls;
	}
}
