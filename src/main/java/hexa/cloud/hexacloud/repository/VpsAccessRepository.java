package hexa.cloud.hexacloud.repository;

import hexa.cloud.hexacloud.model.VpsAccess;
import hexa.cloud.hexacloud.model.VpsAccessId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VpsAccessRepository extends JpaRepository<VpsAccess, VpsAccessId> {
    
}