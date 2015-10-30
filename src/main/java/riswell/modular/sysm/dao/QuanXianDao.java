package riswell.modular.sysm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.sysm.domain.QuanXian;

public interface QuanXianDao extends JpaRepository<QuanXian, Long>, JpaSpecificationExecutor<QuanXian>
{
	List<QuanXian> findByUrl(String url);
}
