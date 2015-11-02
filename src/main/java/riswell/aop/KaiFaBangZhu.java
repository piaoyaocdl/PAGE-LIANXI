package riswell.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 这个类主要是用来，开发的时候输出语句，方便开发
 * 
 * @author HSW
 *
 */
@Aspect
@Component
public class KaiFaBangZhu
{

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void qiedian()
	{

	}

	@Before(value = "qiedian()")
	public void before()
	{
//		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		// request 请求
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//		String url = "@RequestMapping(\"" + request.getRequestURL().toString().split("9090")[1];
//		Map<String, String[]> pm = request.getParameterMap();
//		// 用户信息
//		String yonghu = (String) request.getSession().getAttribute("yonghuming");
//		System.out.println("----开始-------------------方法执行前-----------------------");
//		System.out.println("用户：" + yonghu + "，发出的请求：" + url);
//		System.out.println("携带参数：");
//		for (String key : pm.keySet())
//		{
//			System.out.println("		-k-  " + key);
//			for (int i = 0; i < pm.get(key).length; i++)
//			{
//				System.out.println("			-v-  " + pm.get(key)[i]);
//			}
//		}
//		System.out.println("----结束-------------------方法执行前-----------------------");
	}

	@AfterReturning(value = "qiedian()", returning = "re")
	public void afterReturning(Object re)
	{
//		System.out.println("----开始--------------------方法返回值执行-----------------------");
//		if (re instanceof String)
//		{
//			String fanhui = (String) re;
//			System.out.println("系统返回 ：" + fanhui);
//		}else if (re instanceof Map)
//		{
//			Map<?, ?> fanhui=(Map<?, ?>) re;
//			System.out.println("系统返回 ：" + fanhui);
//		}
//		System.out.println("----结束-------------------方法返回值执行-----------------------");
	}

	@AfterThrowing(value = "qiedian()")
	public void throwss()
	{
		// System.out.println("方法异常时执行.....");
	}

	@After(value = "qiedian()")
	public void after()
	{
		// System.err.println("————————华丽的分隔符————————华丽的分隔符————————华丽的分隔符————————华丽的分隔符————————");
	}
}
