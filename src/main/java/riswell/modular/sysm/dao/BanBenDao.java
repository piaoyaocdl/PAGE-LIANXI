package riswell.modular.sysm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import riswell.modular.sysm.domain.BanBen;

public interface BanBenDao extends JpaRepository<BanBen	, Long>,JpaSpecificationExecutor<BanBen>
{

}
