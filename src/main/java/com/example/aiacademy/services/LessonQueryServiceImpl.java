package com.example.aiacademy.services;

import com.example.aiacademy.dto.*;
import com.example.aiacademy.models.*;
import com.example.aiacademy.repositories.LessonRepository;
import com.example.aiacademy.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonQueryServiceImpl implements LessonQueryService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public List<LessonResponse> getLessonsByModuleId(Long moduleId) {

        moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        return lessonRepository.findByModuleIdOrderById(moduleId)
                .stream()
                .map(this::mapLesson)
                .collect(Collectors.toList());
    }

    private LessonResponse mapLesson(Lesson l) {
        switch (l.getLessonType()) {
            case VIDEO -> {
                return LessonResponse.builder()
                        .type("VIDEO")
                        .lesson(VideoLessonResponse.fromEntity((VideoLesson) l))
                        .build();
            }
            case PDF -> {
                return LessonResponse.builder()
                        .type("PDF")
                        .lesson(PdfLessonResponse.fromEntity((PdfLesson) l))
                        .build();
            }
            case TEXT -> {
                return LessonResponse.builder()
                        .type("TEXT")
                        .lesson(TextLessonResponse.fromEntity((TextLesson) l))
                        .build();
            }
            case QUIZ -> {
                return LessonResponse.builder()
                        .type("QUIZ")
                        .lesson(QuizLessonResponse.fromEntity((QuizLesson) l))
                        .build();
            }
            default -> throw new RuntimeException("Unsupported lesson type!");
        }
    }
}
