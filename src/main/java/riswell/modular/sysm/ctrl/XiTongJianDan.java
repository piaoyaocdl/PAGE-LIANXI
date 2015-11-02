package riswell.modular.sysm.ctrl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import riswell.modular.sysm.domain.WenJian;
import riswell.util.GongJu;

/**
 * 系统的简单 Controller
 * 
 * @author HSW
 *
 */
@Controller
public class XiTongJianDan
{
	@RequestMapping("/")
	public String daodengluyemian()
	{
		return GongJu.重定向 + "/RISwell/RISwell.html";
	}

	/**
	 * 登陆系统
	 * 
	 * @param yemian
	 * @return
	 */
	@RequestMapping("/denglu")
	@ResponseBody
	public String denglu(String zhanghao, String mima, Boolean jizhuwo)
	{
		UsernamePasswordToken token = new UsernamePasswordToken(zhanghao, GongJu.jiami_mima(mima));

		String re = "chenggong";
		Subject currentUser = SecurityUtils.getSubject();
		try
		{
			// 登陆
			currentUser.login(token);
			currentUser.getSession().setAttribute("yonghuming", zhanghao);
			if (jizhuwo == true)
			{
				token.setRememberMe(true);
			}
		} catch (Exception uae)
		{
			re = "shibai";
		}
		return re;
	}

	/**
	 * 登出系统
	 * 
	 * @return
	 */
	@RequestMapping("/dengchu")
	@ResponseBody
	public String dengchu()
	{
		SecurityUtils.getSubject().logout();
		return "ok";
	}

	/**
	 * 上传文件，
	 * 
	 * @param file
	 *            文件数据
	 * @param wenjianming
	 *            服务器端的名字
	 * @param Filename
	 *            原文件名字
	 * @return
	 */
	@RequestMapping("/shangchuanwenjian")
	@ResponseBody
	public String wenjianshangchuan(MultipartFile file, String wenjianming, String Filename)
	{
		if (!file.isEmpty())
		{
			byte[] bytes;

			BufferedOutputStream stream = null;
			try
			{
				bytes = file.getBytes();
				stream = new BufferedOutputStream(
						new FileOutputStream(new File(GongJu.getXiangMuLuJing() + "上传文件/" + wenjianming)));
				stream.write(bytes);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				if (stream != null)
				{

					try
					{
						stream.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return "OK";
	}

	/**
	 * 系统所有级别的下载文件
	 * 
	 * @param json
	 *            WenJian类的JSON格式化
	 * @return
	 */
	@RequestMapping("/xiazaiwenjian")
	@ResponseBody
	public ResponseEntity<byte[]> xiazaiwenjian(String json)
	{
		WenJian wenJian = JSON.parseObject(json, WenJian.class);
		ResponseEntity<byte[]> ls = null;
		try
		{
			ls = GongJu.xiazai(wenJian.getXinwenjianming(), wenJian.getYuanwenjianming());
		} catch (IOException e)
		{
		}
		return ls;
	}
}
