package com.example.aiacademy.services;

import com.example.aiacademy.dto.PdfLessonRequest;
import com.example.aiacademy.dto.PdfLessonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PdfLessonService {

    PdfLessonResponse create(Long moduleId, String tutorEmail, PdfLessonRequest  request);
    PdfLessonResponse uploadPdf(Long lessonId, String tutorEmail, MultipartFile file);
}
