package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.QuizLessonRequest;
import com.example.aiacademy.services.QuizLessonService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons/quiz")
@RequiredArgsConstructor
public class QuizLessonController {

    private final QuizLessonService quizLessonService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable Long moduleId,
                                 @RequestBody QuizLessonRequest request,
                                 Principal principal) {
        return ResponseEntity.ok(
                quizLessonService.create(moduleId, principal.getName(), request)
        );
    }
}
