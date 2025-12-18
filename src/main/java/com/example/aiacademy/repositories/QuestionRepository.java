package com.example.aiacademy.repositories;

import com.example.aiacademy.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
