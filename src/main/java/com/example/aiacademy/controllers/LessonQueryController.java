package com.example.aiacademy.controllers;

import com.example.aiacademy.services.LessonQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons")
@RequiredArgsConstructor
public class LessonQueryController {

    private final LessonQueryService lessonQueryService;

    @GetMapping
    public ResponseEntity<?> getLessons(@PathVariable Long moduleId) {
        return ResponseEntity.ok(
                lessonQueryService.getLessonsByModuleId(moduleId)
        );
    }
}
