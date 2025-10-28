package com.example.aiacademy.repositories;

import com.example.aiacademy.models.ERole;
import com.example.aiacademy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long aLong);

    Optional<Role> findByName(ERole name);
}
