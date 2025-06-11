package hexa.cloud.hexacloud.security;

import hexa.cloud.hexacloud.model.Role;
import hexa.cloud.hexacloud.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    CommandLineRunner ensureDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER") == null) {
                roleRepository.save(new Role("USER"));
            }
            // Nếu muốn có thêm ADMIN mặc định:
            if (roleRepository.findByName("ADMIN") == null) {
                roleRepository.save(new Role("ADMIN"));
            }
        };
    }
}