package com.example.aiacademy.services;

import com.example.aiacademy.dto.LessonResponse;

import java.util.List;

public interface LessonQueryService {

    List<LessonResponse> getLessonsByModuleId(Long moduleId);
}
