package riswell.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import riswell.util.GongJu;

/**
 * 记录系统运行期间的异常
 * 
 * @author HSWC
 *
 */
@Aspect
@Component
public class YiChangJiLu
{
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void qiedian()
	{

	}

	/**
	 * 记录异常信息到文件中
	 * 
	 * @param qiedian
	 * @param yichang
	 */
	@AfterThrowing(value = "qiedian()", throwing = "yichang")
	public void afterThrowing(JoinPoint qiedian, Exception yichang)
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String url = "@RequestMapping(\"" + request.getRequestURL().toString().split("9090")[1];
		Map<String, String[]> pm = request.getParameterMap();
		String yonghu = (String) request.getSession().getAttribute("yonghuming");
		File jilu = new File(GongJu.getXiangMuLuJing() + "异常记录/" + yonghu + "--"
				+ (new SimpleDateFormat("yyyy-MM-dd HH：mm：ss")).format(new Date()) + ".txt");
		BufferedWriter writer = null;

		try
		{
			jilu.createNewFile();

			writer = new BufferedWriter(new FileWriter(jilu, true));

			writer.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\r\n");
			writer.write("异常开始" + "\r\n");
			writer.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\r\n");
			writer.write("用户：" + yonghu + "，发出的请求：" + url + "\r\n");
			writer.write("携带参数：" + "\r\n");
			for (String key : pm.keySet())
			{
				writer.write("		-k-  " + key + "\r\n");
				for (int i = 0; i < pm.get(key).length; i++)
				{
					writer.write("			-v-  " + pm.get(key)[i] + "\r\n");
				}
			}
			writer.write("异常内容：" + "\r\n");
			writer.write("			" + yichang.getCause().getCause() + "\r\n\r\n");

			writer.write("			" + yichang.getMessage() + "\r\n");

			for (int i = 0; i < yichang.getStackTrace().length; i++)
			{
				writer.write("			" + yichang.getStackTrace()[i].toString() + "\r\n");
			}

			writer.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\r\n");
			writer.write("异常结束" + "\r\n");
			writer.write("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "\r\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (writer != null)
			{

				try
				{
					writer.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

}
