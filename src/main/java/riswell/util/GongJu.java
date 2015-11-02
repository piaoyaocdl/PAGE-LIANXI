package riswell.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class GongJu
{

	/**
	 * 发送邮件
	 * 
	 * @param yichangwenjian
	 *            异常的记录文件
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void fasongyoujian(String yichangwenjian) throws MessagingException
	{
		final Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.163.com");
		props.put("mail.smtp.port", "25");
		// 设置发送者帐号密码
		props.put("mail.user", "ris_yichangjilu@163.com");
		props.put("mail.password", "yichangjilu");

		Authenticator authenticator = new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				String userName = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};
		Session mailSession = Session.getInstance(props, authenticator);
		MimeMessage message = new MimeMessage(mailSession);
		InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
		message.setFrom(form);

		// 设置收件人
		InternetAddress to = new InternetAddress("ris_yichangjilu@163.com");
		message.setRecipient(RecipientType.TO, to);

		// 设置邮件标题
		message.setSubject("异常记录" + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));

		// 设置邮件的内容体
		message.setContent(readTxtFile(yichangwenjian), "text/html;charset=UTF-8");

		Transport.send(message);
	}

	/**
	 * 读取文本文件
	 * 
	 * @param filePathAndName
	 *            文件的路径和名称
	 * @throws IOException
	 */
	public static String readTxtFile(String filePathAndName)
	{

		String re = "文件没有找到";
		StringBuilder ls = new StringBuilder("");
		String encoding = "UTF-8";

		File file = new File(filePathAndName);
		if (file.exists() && file.isFile())
		{
			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			try
			{
				read = new InputStreamReader(new FileInputStream(file), encoding);
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null)
				{
					ls.append("<p>" + lineTxt + "<p>");
				}
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				if (bufferedReader != null)
				{

					try
					{
						bufferedReader.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				if (read != null)
				{

					try
					{
						read.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		if (ls.length() >= 20)
		{
			re = ls.toString();
		}
		return re;

	}

	/**
	 * Spring MVC 的重定向
	 */
	public static final String 重定向 = "redirect:";

	/**
	 * 把数组转换为HashSet
	 * 
	 * @param shuzu
	 *            需要转换的数组
	 * @return 转化后的 Set
	 */
	public static <E> Set<E> shuzu2set(E[] shuzu)
	{
		Set<E> re = new HashSet<>();

		for (int i = 0; i < shuzu.length; i++)
		{
			re.add(shuzu[i]);
		}
		return re;
	}

	/**
	 * List 转换为 Set
	 * 
	 * @param list
	 * @return
	 */
	public static <E> Set<E> list2set(List<E> list)
	{
		Set<E> re = new HashSet<>();
		re.addAll(list);
		return re;
	}

	/**
	 * 密码加密函数
	 * 
	 * @param yuanmima
	 *            原密码
	 * @return 加密后密码
	 */
	public static String jiami_mima(String yuanmima)
	{
		return new Md5Hash(yuanmima, "!@#$&^").toString();
	}

	/**
	 * 获得项目部署的路径
	 * 
	 * @return
	 */
	public static String getXiangMuLuJing()
	{
		String ls = GongJu.class.getClassLoader().getResource("").getPath();
		String re = null;
		if (ls.indexOf(".jar") >= 0)
		{
			re = ls.substring(6, ls.indexOf(".jar") - 7);
		}

		if (ls.indexOf("target") >= 0)
		{
			re = ls.substring(1, ls.indexOf("target"));
		}
		return re;
	}

	/**
	 * 下载文件,只能用于controller 调用
	 * 
	 * @param wenjian
	 *            要下载的文件
	 * @param baocunwei
	 *            要保存为
	 *
	 */
	public static ResponseEntity<byte[]> xiazai(String wenjian, String wenjianjia, String baocunwei) throws IOException
	{
		String path = GongJu.getXiangMuLuJing() + wenjianjia + "/" + wenjian;
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", new String(baocunwei.getBytes(), "ISO8859-1"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	public static ResponseEntity<byte[]> xiazai(String wenjian, String baocunwei) throws IOException
	{
		return GongJu.xiazai(wenjian, "上传文件", baocunwei);
	}

	/**
	 * 下载模版
	 * 
	 * @param mobanming
	 * @return
	 */
	public static URL getReportTemplate(String mobanming)
	{
		return ClassLoader.getSystemResource("ReportTemplate/" + mobanming);
	}
}
