package riswell.modular.sysm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.sysm.domain.JueSe;

public interface JueSeDao extends JpaRepository<JueSe, Long>, JpaSpecificationExecutor<JueSe>
{
	List<JueSe> findByJueseming(String jueseming);
}
