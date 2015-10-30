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
public class QuanXian
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=50,nullable=false,unique=true)
	private String quanxianming;
	
	@Column(length=100,nullable=false,unique=true)
	private String url;
	
	@Column(length=300,nullable=false,unique=true)
	private String miaoshu;
	
	@ManyToMany(mappedBy="quanxians")
	private Set<JueSe> jueses;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date gengxinshijian;
	
}
