package riswell.modular.sysm.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JueSe
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String jueseming;
	
	@ManyToMany(mappedBy="jueses")
	private Set<YongHu> yonghus;
	
	@ManyToMany
	private Set<QuanXian> quanxians;
	
	@Column(length = 200, nullable = false, unique = true)
	private String miaoshu;

}
