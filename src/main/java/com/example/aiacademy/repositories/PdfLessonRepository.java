package com.example.aiacademy.repositories;

import com.example.aiacademy.models.PdfLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfLessonRepository extends JpaRepository<PdfLesson,Long> {
}
