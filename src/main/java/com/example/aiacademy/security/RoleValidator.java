package com.example.aiacademy.security;

import com.example.aiacademy.models.User;
import org.springframework.stereotype.Service;

@Service
public class RoleValidator {

    public boolean isTutor(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().name().equals("ROLE_TUTOR") ||
                        r.getName().name().equals("ROLE_ADMIN"));
    }

    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().name().equals("ROLE_ADMIN"));
    }
}
