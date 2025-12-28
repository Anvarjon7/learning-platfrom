package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.QuizLessonRequest;
import com.example.aiacademy.services.QuizLessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons/quiz")
@RequiredArgsConstructor
public class QuizLessonController {

    private final QuizLessonService quizLessonService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @PathVariable Long moduleId,
            @org.springframework.web.bind.annotation.RequestBody
            @Valid QuizLessonRequest request,
            Principal principal
    ) {
        System.out.println("REQUEST = " + request);
        System.out.println("QUESTIONS = " + request.getQuestions());

        return ResponseEntity.ok(
                quizLessonService.create(moduleId, principal.getName(), request)
        );
    }
}
