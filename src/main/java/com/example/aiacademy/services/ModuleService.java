package com.example.aiacademy.services;

import com.example.aiacademy.dto.ModuleRequest;
import com.example.aiacademy.dto.ModuleResponse;

import java.util.List;

public interface ModuleService {
    ModuleResponse createModule(Long courseId, String tutorEmail, ModuleRequest request);
    List<ModuleResponse> getModulesByCourse(Long courseId);
}
