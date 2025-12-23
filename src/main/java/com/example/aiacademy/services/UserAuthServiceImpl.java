package com.example.aiacademy.services;

import com.example.aiacademy.models.ERole;
import com.example.aiacademy.models.Role;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.RoleRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String register(String email, String password, String fullName, Set<String> roleNames) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Default role
        if (roleNames == null || roleNames.isEmpty()) {
            roleNames = Set.of("ROLE_STUDENT");
        }

        Set<Role> roles = new HashSet<>();

        for (String roleName : roleNames) {
            ERole eRole;
            try {
                eRole = ERole.valueOf(roleName);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid role: " + roleName);
            }

            Role role = roleRepository.findByName(eRole)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

            roles.add(role);
        }

        User user = new User(
                email,
                passwordEncoder.encode(password),
                fullName,
                roles
        );

        userRepository.save(user);

        // ✅ IMPORTANT: convert Role entities → role names
        Set<String> roleClaims = roles.stream()
                .map(role -> role.getName().name()) // ROLE_TUTOR
                .collect(Collectors.toSet());

        return jwtService.generateToken(user.getEmail(), roleClaims);
    }

    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ IMPORTANT: convert Role entities → role names
        Set<String> roleClaims = user.getRoles().stream()
                .map(role -> role.getName().name()) // ROLE_TUTOR
                .collect(Collectors.toSet());

        return jwtService.generateToken(user.getEmail(), roleClaims);
    }
}
