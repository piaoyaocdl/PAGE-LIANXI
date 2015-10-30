package riswell.modular.zuzhipeixing.linchuanghla.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.zuzhipeixing.linchuanghla.domain.JianCeShenQingDan;

public interface JianCeShenQingDanDao
		extends JpaRepository<JianCeShenQingDan, Long>, JpaSpecificationExecutor<JianCeShenQingDan>
{

}
