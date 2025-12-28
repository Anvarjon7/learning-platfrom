package com.example.aiacademy.services;

import com.example.aiacademy.dto.QuestionRequest;
import com.example.aiacademy.dto.QuizLessonRequest;
import com.example.aiacademy.dto.QuizLessonResponse;
import com.example.aiacademy.models.Module;
import com.example.aiacademy.models.*;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.QuestionRepository;
import com.example.aiacademy.repositories.QuizLessonRepository;
import com.example.aiacademy.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizLessonServiceImpl implements QuizLessonService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final QuizLessonRepository quizLessonRepository;
    private final QuestionRepository questionRepository;

    //    @TutorOnly
    @Transactional
    @Override
    public QuizLessonResponse create(Long moduleId, String tutorEmail, QuizLessonRequest request) {

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new RuntimeException("Quiz must contain at least one question");
        }

        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(tutorEmail);
                    u.setPassword("TEMP");
                    u.setFullname("TEMP");
                    return userRepository.save(u);
                });

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        QuizLesson lesson = new QuizLesson();
        lesson.setModule(module);
        lesson.setLessonType(Lesson.LessonType.QUIZ);
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());

        for (QuestionRequest q : request.getQuestions()) {
            Question question = new Question();
            question.setPrompt(q.getPrompt());
            question.setChoices(q.getChoices());
            question.setCorrectAnswerIndex(q.getCorrectAnswerIndex());
            question.setQuizLesson(lesson);
            lesson.getQuestions().add(question);
        }

        quizLessonRepository.save(lesson);

        return QuizLessonResponse.fromEntity(lesson);
    }
}
