package riswell.config;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import riswell.modular.sysm.dao.BanBenDao;
import riswell.modular.sysm.dao.JueSeDao;
import riswell.modular.sysm.dao.QuanXianDao;
import riswell.modular.sysm.dao.YongHuDao;
import riswell.modular.sysm.domain.BanBen;
import riswell.modular.sysm.domain.JueSe;
import riswell.modular.sysm.domain.QuanXian;
import riswell.modular.sysm.domain.YongHu;
import riswell.util.GongJu;

/**
 * @author HSW 实现数据库初始化
 */
@Component
public class ChuShiHua implements ApplicationListener<ApplicationReadyEvent>
{

	@Autowired
	YongHuDao yonghudao;
	@Autowired
	JueSeDao juesedao;
	@Autowired
	QuanXianDao quanxiandao;
	@Autowired
	BanBenDao banbendao;

	@Override
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event)
	{
		/*********************************************************************************
		 * 
		 * 数据库初始化
		 * 
		 *********************************************************************************/

		Date dangqianshijian = new Date();

		// ----------版本设置----------
		BanBen banben = new BanBen();
		banben.setChengxubanben("测试版本");
		banben.setShujukubanben("测试版本");
		banben.setKaishiriqi(dangqianshijian);
		banbendao.save(banben);

		// ----------数据库初始化----------
		// 权限数据
		QuanXian quanxian = null;
		List<QuanXian> qxs = null;

		// 权限数据-开始copy

		qxs = quanxiandao.findByUrl("/ceshi");
		if (qxs.size() >= 1)
		{
			quanxian = qxs.get(0);
		} else
		{
			quanxian = new QuanXian();
		}
		quanxian.setUrl("/ceshi");
		quanxian.setGengxinshijian(dangqianshijian);
		quanxian.setMiaoshu("测试");
		quanxian.setQuanxianming("测试");
		quanxiandao.saveAndFlush(quanxian);

		// 权限数据-结束copy

		// 角色数据
		List<JueSe> jueses = juesedao.findByJueseming("测试");
		JueSe juese = null;
		if (jueses.size() >= 1)
		{
			juese = jueses.get(0);
		} else
		{
			juese = new JueSe();
		}
		juese.setJueseming("测试");
		juese.setMiaoshu("系统管理员，拥有所有权限。");
		List<QuanXian> quanxians = quanxiandao.findAll();
		juese.setQuanxians(new HashSet<>(quanxians));
		juese = juesedao.saveAndFlush(juese);

		// 用户数据
		List<YongHu> yonghus = yonghudao.findByZhanghao("ceshi");
		YongHu yonghu = null;
		if (yonghus.size() >= 1)
		{
			yonghu = yonghus.get(0);
		} else
		{
			yonghu = new YongHu();
		}

		yonghu.setZhanghao("ceshi");
		yonghu.setMima(GongJu.jiami_mima("ceshi"));
		yonghu.setChuangjianriqi(dangqianshijian);
		yonghu.setJueses(GongJu.list2set(juesedao.findByJueseming("测试")));
		yonghudao.saveAndFlush(yonghu);

		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("数据库初始化完成！");
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("");
		System.err.println("");

		/*********************************************************************************
		 * 
		 * 文件夹初始化
		 * 
		 *********************************************************************************/
		File file = null;

		file = new File(GongJu.getXiangMuLuJing() + "临时文件");
		if (!file.exists() && !file.isDirectory())
		{
			file.mkdir();
		}
		
		file = new File(GongJu.getXiangMuLuJing() + "上传文件");
		if (!file.exists() && !file.isDirectory())
		{
			file.mkdir();
		}
		
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("文件夹初始化完成！");
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("");
		System.err.println("");
		
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("项目路径：");
		System.err.println("		"+GongJu.getXiangMuLuJing());
		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.err.println("");
		System.err.println("");
		
		

	}

}
