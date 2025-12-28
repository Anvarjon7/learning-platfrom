package com.example.aiacademy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LessonProgress lessonProgress;

    private int score;               // percentage
    private int totalQuestions;
    private boolean passed;

    private LocalDateTime attemptedAt;

    @OneToMany(
            mappedBy = "quizAttempt",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuizAttemptAnswer> answers = new ArrayList<>();
}
