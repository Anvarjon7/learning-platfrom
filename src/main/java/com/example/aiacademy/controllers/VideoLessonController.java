package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.VideoLessonRequest;
import com.example.aiacademy.services.VideoLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @PostMapping(
            value = "/upload/{lessonId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadVideo(
            Principal principal,
            @PathVariable Long moduleId,
//            @PathVariable Long lessonId,
            @RequestParam("file") MultipartFile file
    )
    {
        if (file != null || !file.isEmpty()){
            System.out.println("file " + file.getName());
        }
        return ResponseEntity.ok(videoLessonService.uploadVideo(moduleId, principal.getName(), file));
    }
}
