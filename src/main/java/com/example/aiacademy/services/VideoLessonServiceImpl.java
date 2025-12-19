package com.example.aiacademy.services;

import com.example.aiacademy.dto.VideoLessonRequest;
import com.example.aiacademy.dto.VideoLessonResponse;
import com.example.aiacademy.models.Lesson;
import com.example.aiacademy.models.Module;
import com.example.aiacademy.models.User;
import com.example.aiacademy.models.VideoLesson;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.repositories.VideoLessonRepository;
import com.example.aiacademy.security.annotations.TutorOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoLessonServiceImpl implements VideoLessonService{

    private final ModuleRepository moduleRepository;
    private final VideoLessonRepository videoLessonRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;


    @TutorOnly
    @Override
    public VideoLessonResponse createLesson(Long moduleId, String tutorEmail, VideoLessonRequest request) {
        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseThrow(() -> new RuntimeException("Tutor not found"));


        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        VideoLesson lesson = new VideoLesson();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setDurationSeconds(request.getDurationSeconds());
        lesson.setLessonType(Lesson.LessonType.VIDEO);
        lesson.setModule(module);

        videoLessonRepository.save(lesson);

        return VideoLessonResponse.fromEntity(lesson);
    }

    @TutorOnly
    @Override
    public VideoLessonResponse uploadVideo(Long lessonId, String tutorEmail, MultipartFile file) {

        VideoLesson lesson = videoLessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        String path = fileStorageService.storeFile(file,
                "course-" + lesson.getModule().getCourse().getId() +
                        "/module-" + lesson.getModule().getId());

        lesson.setVideoUrl(path);
        videoLessonRepository.save(lesson);

        return  VideoLessonResponse.fromEntity(lesson);
    }
}
