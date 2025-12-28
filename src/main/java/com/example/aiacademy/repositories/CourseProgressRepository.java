package com.example.aiacademy.repositories;

import com.example.aiacademy.models.Course;
import com.example.aiacademy.models.CourseProgress;
import com.example.aiacademy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {

    Optional<CourseProgress> findByStudentAndCourse(User studentId, Course courseId);
}
