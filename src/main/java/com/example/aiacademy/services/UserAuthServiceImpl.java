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
import java.util.Map;
import java.util.Set;

@Service
public class UserAuthServiceImpl implements UserAuthService{

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtService jwtService;
        private final BCryptPasswordEncoder passwordEncoder;

    public UserAuthServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               JwtService jwtService,
                               BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(String email, String password, String fullName, Set<String> roleNames) {

        if (userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already exist");  //TO-DO own exception
        }
        Set<Role> roles = new HashSet<>();
        if (roleNames == null || roleNames.isEmpty()){
            roleNames = Set.of("ROLE_STUDENT");
        }

        for (String roleName : roleNames){
            ERole eRole;
            try{
                eRole = ERole.valueOf(roleName);
            }catch (IllegalArgumentException e){
                throw new RuntimeException("Role not found " + roleName);
            }

            Role role = roleRepository.findByName((eRole))
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)); //TO-DO custom exception
            roles.add(role);
        }
        User user = new User(email,passwordEncoder.encode(password), fullName,roles);
        userRepository.save(user);

        return jwtService.generateToken(user.getEmail(), roles);
    }

    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")); // TO-DO exception

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid credentials"); // TO-DO exception
        }
        return jwtService.generateToken(user.getEmail(), user.getRoles());
    }
}
