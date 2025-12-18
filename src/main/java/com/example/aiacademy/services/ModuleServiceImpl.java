package com.example.aiacademy.services;

import com.example.aiacademy.dto.ModuleRequest;
import com.example.aiacademy.dto.ModuleResponse;
import com.example.aiacademy.repositories.CourseRepository;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService{

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    @Override
    public ModuleResponse createModule(Long courseId, String tutorEmail, ModuleRequest request) {
        return null;
    }

    @Override
    public List<ModuleResponse> getModulesByCourse(Long courseId) {
        return null;
    }
}
