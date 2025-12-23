package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.PdfLessonRequest;
import com.example.aiacademy.services.PdfLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons/pdf")
@RequiredArgsConstructor
public class PdfLessonController {

    private final PdfLessonService pdfLessonService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable Long moduleId,
                                    @RequestBody PdfLessonRequest request,
                                    Principal principal) {
        return ResponseEntity.ok(
                pdfLessonService.create(moduleId, principal.getName(), request)
        );
    }

    @PostMapping(
            value = "/upload/{lessonId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadPdf(@PathVariable Long lessonId,
                                       @RequestParam("file") MultipartFile file,
                                       Principal principal) {

        if (file != null || !file.isEmpty()) {
            System.out.println("FILE" + file.getName());
        }else {
            System.out.println("File is null");
        }
        return ResponseEntity.ok(
                pdfLessonService.uploadPdf(lessonId, principal.getName(), file)
        );
    }
}
