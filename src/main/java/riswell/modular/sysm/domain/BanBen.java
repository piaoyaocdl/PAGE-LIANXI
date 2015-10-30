package riswell.modular.sysm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BanBen
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, nullable = false)
	private String chengxubanben;

	@Column(length = 50, nullable = false)
	private String shujukubanben;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date kaishiriqi;
}
