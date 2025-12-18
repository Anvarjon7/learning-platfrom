package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.VideoLessonRequest;
import com.example.aiacademy.services.VideoLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons/video")
@RequiredArgsConstructor
public class VideoLessonController {

    private final VideoLessonService videoLessonService;

    @PostMapping
    public ResponseEntity<?> createLesson(
            Principal principal,
            @PathVariable Long moduleId,
            @RequestBody VideoLessonRequest request
    ) {
        return ResponseEntity.ok(videoLessonService.createLesson(moduleId, principal.getName(), request));
    }

    @PostMapping("/{lessonId}/upload")
    public ResponseEntity<?> uploadVideo(
            Principal principal,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(videoLessonService.uploadVideo(lessonId, principal.getName(), file));
    }
}
