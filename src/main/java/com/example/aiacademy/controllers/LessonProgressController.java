package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.QuizSubmissionRequest;
import com.example.aiacademy.dto.QuizSubmissionResponse;
import com.example.aiacademy.models.Lesson;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.LessonRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.services.StudentProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonProgressController {

    private final StudentProgressService studentProgressService;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @PostMapping("/{lessonId}/complete")
    public ResponseEntity<?> completeLesson(
            Principal principal,
            @PathVariable Long lessonId
    ) {
        User student = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        studentProgressService.completeLesson(student, lesson);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{lessonId}/quiz/submit")
    public ResponseEntity<QuizSubmissionResponse> submitQuiz(
            Principal principal,
            @PathVariable Long lessonId,
            @Valid @RequestBody QuizSubmissionRequest request
    ) {
        User student = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        return ResponseEntity.ok(
                studentProgressService.submitQuiz(student, lesson, request)
        );
    }
}

