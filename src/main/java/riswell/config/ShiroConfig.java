package riswell.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig
{

	@Bean
	@Autowired
	public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm shiroRealm)
	{
		DefaultWebSecurityManager re = new DefaultWebSecurityManager();
		re.setRealm(shiroRealm);
		return re;
	}

	@Bean
	@Autowired
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager)
	{
		ShiroFilterFactoryBean re = new ShiroFilterFactoryBean();
		Map<String, String> fcm = new HashMap<>();
		
		// 静态资源
		fcm.put("/RISwell/**", "anon");
		
		// 用户级别
		fcm.put("/**", "user");
		// 授权级别
		
		// 匿名级别
		fcm.put("/denglu", "anon");
		fcm.put("/", "anon");
		re.setFilterChainDefinitionMap(fcm);
		re.setSecurityManager(defaultWebSecurityManager);
		re.setLoginUrl("/");
		re.setUnauthorizedUrl("/");
		return re;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
	{
		return new LifecycleBeanPostProcessor();
	}
}
