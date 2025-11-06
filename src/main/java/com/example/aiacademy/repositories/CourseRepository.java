package com.example.aiacademy.repositories;

import com.example.aiacademy.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
