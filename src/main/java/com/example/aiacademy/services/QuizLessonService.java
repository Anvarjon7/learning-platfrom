package com.example.aiacademy.services;

import com.example.aiacademy.dto.QuizLessonRequest;
import com.example.aiacademy.dto.QuizLessonResponse;

public interface QuizLessonService {
    QuizLessonResponse create(Long moduleId, String tutorEmail, QuizLessonRequest request);
}
