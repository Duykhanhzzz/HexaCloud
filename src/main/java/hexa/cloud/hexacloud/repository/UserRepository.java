package hexa.cloud.hexacloud.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import hexa.cloud.hexacloud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}