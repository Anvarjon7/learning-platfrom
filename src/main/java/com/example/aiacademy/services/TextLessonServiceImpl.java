package com.example.aiacademy.services;

import com.example.aiacademy.dto.TextLessonRequest;
import com.example.aiacademy.dto.TextLessonResponse;
import com.example.aiacademy.models.Lesson;
import com.example.aiacademy.models.Module;
import com.example.aiacademy.models.TextLesson;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.TextLessonRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.security.annotations.TutorOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextLessonServiceImpl implements TextLessonService{

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final TextLessonRepository textLessonRepository;

    @TutorOnly
    @Override
    public TextLessonResponse create(Long moduleId, String tutorEmail, TextLessonRequest request) {

        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(tutorEmail);
                    u.setPassword("TEMP");   // placeholder for MVP
                    u.setFullname("TEMP");
                    return userRepository.save(u);
                });


        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        TextLesson lesson = new TextLesson();
        lesson.setModule(module);
        lesson.setLessonType(Lesson.LessonType.TEXT);
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setContent(request.getContent());

        textLessonRepository.save(lesson);

        return TextLessonResponse.fromEntity(lesson);
    }
}
