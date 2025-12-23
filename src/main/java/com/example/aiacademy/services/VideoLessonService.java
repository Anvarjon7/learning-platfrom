package com.example.aiacademy.services;

import com.example.aiacademy.dto.VideoLessonRequest;
import com.example.aiacademy.dto.VideoLessonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoLessonService {

    VideoLessonResponse createLesson(Long moduleId, String tutorEmail, VideoLessonRequest request);

    VideoLessonResponse uploadVideo(Long lessonId, String tutorEmail, MultipartFile file);
}
