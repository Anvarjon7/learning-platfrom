package com.example.aiacademy.repositories;

import com.example.aiacademy.models.CourseProgress;
import com.example.aiacademy.models.Lesson;
import com.example.aiacademy.models.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByCourseProgressAndLesson(CourseProgress progress, Lesson lesson);
}
