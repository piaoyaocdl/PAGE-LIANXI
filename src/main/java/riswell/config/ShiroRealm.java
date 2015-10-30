package riswell.config;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import riswell.modular.sysm.dao.YongHuDao;
import riswell.modular.sysm.domain.JueSe;
import riswell.modular.sysm.domain.QuanXian;
import riswell.modular.sysm.domain.YongHu;

/**
 * 
 * SHIRO Realm , 这个类涉及用户的身份验证、权限验证。
 * 
 * @author HSW
 *
 */
@Component
public class ShiroRealm extends AuthorizingRealm
{

	@Autowired
	private YongHuDao yonghudao;

	/**
	 * 获得用户权限
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		String username = (String) principals.fromRealm(getName()).iterator().next();

		if (username == null)
		{
			return null;
		}

		List<YongHu> yonghu = yonghudao.findAll(new Specification<YongHu>()
		{

			@Override
			public Predicate toPredicate(Root<YongHu> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				Predicate ls = cb.equal(root.get("zhanghao").as(String.class), username);
				return query.where(ls).getRestriction();
			}
		});
		if (yonghu.size() != 1)
		{
			return null;
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		YongHu weiyiyonghu = yonghu.get(0);
		for (JueSe jueSe : weiyiyonghu.getJueses())
		{
			info.addRole(jueSe.getJueseming());
			for (QuanXian quanXian : jueSe.getQuanxians())
			{
				info.addStringPermission(quanXian.getQuanxianming());
			}
		}

		return info;

	}

	/**
	 * 验证用户身份
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		if (username == null || username.trim().equals(""))
		{
			return null;
		}
		
		List<YongHu> account = yonghudao.findAll(new Specification<YongHu>()
		{

			@Override
			public Predicate toPredicate(Root<YongHu> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				Predicate ls = cb.equal(root.get("zhanghao").as(String.class), username);
				return query.where(ls).getRestriction();
			}
		});
		
		if (account.size() != 1)
		{
			return null;
		}
		YongHu yonghu = account.get(0);
		return new SimpleAuthenticationInfo(yonghu.getZhanghao(), yonghu.getMima(), getName());
	}

}