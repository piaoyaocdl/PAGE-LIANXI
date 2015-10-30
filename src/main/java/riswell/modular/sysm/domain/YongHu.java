package riswell.modular.sysm.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class YongHu
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String zhanghao;

	@Column(length = 200, nullable = false, unique = false)
	private String mima;

	@ManyToMany
	private Set<JueSe> jueses;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date chuangjianriqi;
}
