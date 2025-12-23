package com.example.aiacademy.config;

import com.example.aiacademy.models.ERole;
import com.example.aiacademy.models.Role;
import com.example.aiacademy.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            for (ERole roleEnum : ERole.values()) {
                roleRepository.findByName(roleEnum)
                        .orElseGet(() -> {
                            Role role = new Role();
                            role.setName(roleEnum);
                            return roleRepository.save(role);
                        });
            }
        };
    }
}
