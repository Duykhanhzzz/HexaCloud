package hexa.cloud.hexacloud.repository;

import hexa.cloud.hexacloud.model.VpsAccess;
import hexa.cloud.hexacloud.model.VpsAccessId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VpsAccessRepository extends JpaRepository<VpsAccess, VpsAccessId> {
    List<VpsAccess> findByUserId(Long userId);
    List<VpsAccess> findByVpsId(Integer vpsId);
}