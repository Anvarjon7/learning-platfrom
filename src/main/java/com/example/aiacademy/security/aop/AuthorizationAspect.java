package com.example.aiacademy.security.aop;

import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.security.RoleValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {
    private final UserRepository userRepository;
    private final RoleValidator roleValidator;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Before("@annotation(com.example.aiacademy.security.annotations.TutorOnly)")
    public void checkTutorAccess() {
        User user = getCurrentUser();
        if (!roleValidator.isTutor(user)) {
            throw new RuntimeException("Access denied: Tutor or Admin required.");
        }
    }

    @Before("@annotation(com.example.aiacademy.security.annotations.AdminOnly)")
    public void checkAdminAccess() {
        User user = getCurrentUser();
        if (!roleValidator.isAdmin(user)) {
            throw new RuntimeException("Access denied: Admin required.");
        }
    }

}
