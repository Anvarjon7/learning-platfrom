package com.example.aiacademy.services;

import com.example.aiacademy.dto.QuizSubmissionRequest;
import com.example.aiacademy.dto.QuizSubmissionResponse;
import com.example.aiacademy.models.*;
import com.example.aiacademy.repositories.CourseProgressRepository;
import com.example.aiacademy.repositories.LessonProgressRepository;
import com.example.aiacademy.repositories.QuestionRepository;
import com.example.aiacademy.repositories.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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



    @Transactional
    public QuizSubmissionResponse submitQuiz(User student, Lesson lesson, QuizSubmissionRequest request) {
        if (!(lesson instanceof QuizLesson quizLesson)) {
            throw new RuntimeException("Lesson is not a quiz");
        }

        Course course = quizLesson.getModule().getCourse();
        CourseProgress cp = accessCourse(student, course);

        LessonProgress lp = lessonProgressRepository.findByCourseProgressAndLesson(cp, quizLesson)
                .orElseGet(() -> {
                    LessonProgress p = new LessonProgress();
                    p.setCourseProgress(cp);
                    p.setLesson(quizLesson);
                    return p;
                });

        List<Question> questions = quizLesson.getQuestions();
        if (questions == null || questions.isEmpty()) {
            throw new RuntimeException("Quiz has no questions");
        }

        Map<Long, Question> questionById = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        int totalQuestions = questions.size();
        int correctCount = 0;

        QuizAttempt attempt = new QuizAttempt();
        attempt.setLessonProgress(lp);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setAttemptedAt(LocalDateTime.now());

        for (var a : request.getAnswers()) {
            Question q = questionById.get(a.getQuestionId());
            if (q == null) {
                throw new RuntimeException("Question not found: " + a.getQuestionId());
            }

            int selected = a.getSelectedAnswerIndex();
            Integer correctIndex = q.getCorrectAnswerIndex();
            boolean correct = correctIndex != null && selected == correctIndex;
            if (correct) {
                correctCount++;
            }

            QuizAttemptAnswer answer = new QuizAttemptAnswer();
            answer.setQuizAttempt(attempt);
            answer.setQuestion(q);
            answer.setSelectedAnswerIndex(selected);
            answer.setCorrect(correct);

            attempt.getAnswers().add(answer);
        }

        int score = (correctCount * 100) / totalQuestions;
        boolean passed = correctCount == totalQuestions;

        attempt.setScore(score);
        attempt.setPassed(passed);

        quizAttemptRepository.save(attempt);

        lp.setCompleted(true);
        lp.setCompletedAt(LocalDateTime.now());
        lessonProgressRepository.save(lp);

        updateCourseCompletion(cp);

        return new QuizSubmissionResponse(score, passed, totalQuestions);
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
