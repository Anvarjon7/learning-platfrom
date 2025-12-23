package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.TextLessonRequest;
import com.example.aiacademy.services.TextLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons/text")
@RequiredArgsConstructor
public class TextLessonController {

    private final TextLessonService textLessonService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable Long moduleId,
                                    @RequestBody TextLessonRequest request,
                                    Principal principal) {
        return ResponseEntity.ok(
                textLessonService.create(moduleId, principal.getName(), request)
        );
    }
}
