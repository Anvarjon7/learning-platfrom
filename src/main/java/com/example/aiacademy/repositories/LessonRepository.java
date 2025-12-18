package com.example.aiacademy.repositories;

import com.example.aiacademy.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

    List<Lesson> findByModuleIdOrderById(Long moduleId);
}
