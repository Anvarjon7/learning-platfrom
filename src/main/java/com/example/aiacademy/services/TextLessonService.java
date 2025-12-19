package com.example.aiacademy.services;

import com.example.aiacademy.dto.TextLessonRequest;
import com.example.aiacademy.dto.TextLessonResponse;

public interface TextLessonService {

    TextLessonResponse create(Long moduleId, String tutorEmail, TextLessonRequest request);

}
