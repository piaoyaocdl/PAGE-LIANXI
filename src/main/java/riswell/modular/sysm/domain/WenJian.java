package riswell.modular.sysm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WenJian
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false)
	private String yuanwenjianming;

	@Column(length = 200, nullable = false)
	private String xinwenjianming;

	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format="yyyy-MM-dd")
	private Date chuangjianshijian;
	
	public static String xinwenjianmingMaker(String yuanwenjianming)
	{
		String[] ls = yuanwenjianming.split("\\.");
		String houzui = ls[ls.length - 1];
		return Double.toString(Math.random()).substring(2) + "-"//
				+ Double.toString(Math.random()).substring(2) + "-"//
				+ Long.toString(System.currentTimeMillis()) + "." + houzui;
	}
}
