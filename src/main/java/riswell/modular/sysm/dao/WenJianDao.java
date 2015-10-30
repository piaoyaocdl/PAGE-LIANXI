package riswell.modular.sysm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.sysm.domain.WenJian;

public interface WenJianDao extends JpaRepository<WenJian, Long>,JpaSpecificationExecutor<WenJian>
{

}
