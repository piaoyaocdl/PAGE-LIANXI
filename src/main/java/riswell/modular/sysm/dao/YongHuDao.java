package riswell.modular.sysm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.sysm.domain.YongHu;

import java.lang.String;
import java.util.List;

public interface YongHuDao extends JpaRepository<YongHu, Long>, JpaSpecificationExecutor<YongHu>
{
	List<YongHu> findByZhanghao(String zhanghao);
}
