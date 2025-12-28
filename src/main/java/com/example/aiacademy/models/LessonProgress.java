package com.example.aiacademy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(
        name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"course_progress_id", "lesson_id"}
        ))
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseProgress courseProgress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    private boolean completed;
    private LocalDateTime completedAt;

    @OneToMany(
            mappedBy = "lessonProgress",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

}
