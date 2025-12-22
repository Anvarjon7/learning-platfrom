package com.example.aiacademy.services;

import com.example.aiacademy.dto.ModuleRequest;
import com.example.aiacademy.dto.ModuleResponse;
import com.example.aiacademy.models.Course;
import com.example.aiacademy.models.Module;
import com.example.aiacademy.repositories.CourseRepository;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService{

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    @Override
    public ModuleResponse createModule(Long courseId, String tutorEmail, ModuleRequest request) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course no found"));

        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setCourse(course);

        moduleRepository.save(module);


        return ModuleResponse.fromEntity(module);
    }

    @Override
    public List<ModuleResponse> getModulesByCourse(Long courseId) {
        return moduleRepository.findByCourseIdOrderById(courseId)
                .stream()
                .map(ModuleResponse::fromEntity)
                .toList();
    }

    @Override
    public ModuleResponse update(Long moduleId, String tutorEmail, ModuleRequest request) {

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());

        moduleRepository.save(module);

        return ModuleResponse.fromEntity(module);
    }

    @Override
    public void delete(Long moduleId, String tutorEmail) {

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        moduleRepository.delete(module);
    }
}
