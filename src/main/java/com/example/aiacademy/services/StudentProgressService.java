package com.example.aiacademy.services;

import com.example.aiacademy.models.*;
import com.example.aiacademy.repositories.CourseProgressRepository;
import com.example.aiacademy.repositories.LessonProgressRepository;
import com.example.aiacademy.repositories.QuestionRepository;
import com.example.aiacademy.repositories.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentProgressService {

    private final CourseProgressRepository courseProgressRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuestionRepository questionRepository;


    @Transactional
    public CourseProgress accessCourse(User student, Course course){

        CourseProgress courseProgress =
                courseProgressRepository.findByStudentAndCourse(student, course)
                        .orElseGet(() -> {
                            CourseProgress p = new CourseProgress();
                            p.setStudent(student);
                            p.setCourse(course);

                            return courseProgressRepository.save(p);
                        });

        courseProgress.setStatus(CourseProgressStatus.IN_PROGRESS);
        courseProgress.setLastAccessedAt(LocalDateTime.now());

        return courseProgress;
    }

    @Transactional
    public void completeLesson(User student, Lesson lesson){

        Course course = lesson.getModule().getCourse();

        CourseProgress cp = accessCourse(student,course);

        LessonProgress lp = lessonProgressRepository.findByCourseProgressAndLesson(cp,lesson)
                .orElseGet(() -> {
                    LessonProgress p = new LessonProgress();
                    p.setCourseProgress(cp);
                    p.setLesson(lesson);
                    return p;
                });

        lp.setCompleted(true);
        lp.setCompletedAt(LocalDateTime.now());

        lessonProgressRepository.save(lp);

        updateCourseCompletion(cp);
    }



    private void updateCourseCompletion(CourseProgress cp){

        int totalLessons =
                cp.getCourse().getModuleList().stream()
                        .mapToInt(m -> m.getLessons().size())
                        .sum();

        long completed =
                cp.getLessonProgresses().stream()
                        .filter(LessonProgress::isCompleted)
                        .count();

        if (completed == totalLessons){
            cp.setStatus(CourseProgressStatus.COMPLETED);
            cp.setCompletedAt(LocalDateTime.now());
        }
    }
}
