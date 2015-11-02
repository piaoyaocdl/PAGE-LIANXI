package riswell.modular.zuzhipeixing.linchuanghla.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import riswell.modular.sysm.domain.WenJian;

@Entity
@Getter
@Setter
public class JianCeShenQingDan
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = true, unique = false)
	private String huanzhexingming;

	@Column(length = 10, nullable = true, unique = false)
	private String xingbie;

	@Temporal(TemporalType.DATE)
	@JSONField(format = "yyyy-MM-dd")
	private Date chushengriqi;

	private Boolean zhuyuan;

	private Boolean menzhen;

	@OneToMany
	private Set<WenJian> fujian;

	@Column(length = 20, nullable = true, unique = false)
	private String shouyangzhe;

	@Column(length = 50, nullable = true, unique = false)
	private String beizhu;

	@Column(length = 20, nullable = true, unique = false)
	private String chuanghao;

	@Column(length = 50, nullable = true, unique = false)
	private String shenfenzhenghao;

	@Column(length = 50, nullable = true, unique = false)
	private String zhuyuanhao;

	@Column(length = 50, nullable = true, unique = false)
	private String lianxidianhua;

	@Column(length = 50, nullable = true, unique = false)
	private String shenqingyiyuan;

	@Column(length = 50, nullable = true, unique = false)
	private String fax;

	@Column(length = 50, nullable = true, unique = false)
	private String shenqingyisheng;

	@Column(length = 50, nullable = true, unique = false)
	private String email;

	@Column(length = 50, nullable = true, unique = false)
	private String youbian;

	@Column(length = 50, nullable = true, unique = false)
	private String tongxundizhi;

	@Column(length = 50, nullable = true, unique = false)
	private String baogaofasongfangshi;

	@Column(length = 50, nullable = true, unique = false)
	private String linchuangzhenduan;

	@Temporal(TemporalType.DATE)
	@JSONField(format = "yyyy-MM-dd")
	private Date shouyangriqi;

	@Temporal(TemporalType.DATE)
	@JSONField(format = "yyyy-MM-dd")
	private Date shengchengjiancedanriqi;

	@Temporal(TemporalType.DATE)
	@JSONField(format = "yyyy-MM-dd")
	private Date caiyangriqi;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date tianjiashijian;
}
