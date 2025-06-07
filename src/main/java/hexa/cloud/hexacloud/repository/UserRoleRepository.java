package hexa.cloud.hexacloud.repository;

import hexa.cloud.hexacloud.model.UserRole;
import hexa.cloud.hexacloud.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(Long userId);
}