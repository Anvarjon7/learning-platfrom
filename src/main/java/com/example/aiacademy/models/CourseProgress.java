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
@Getter @Setter
@Table(
        name = "course_progress",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "course_id"}
        )
)
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private CourseProgressStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime lastAccessedAt;

    @OneToMany(
            mappedBy = "courseProgress",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @PrePersist
    void onCreate() {
        this.status = CourseProgressStatus.NOT_STARTED;
        this.startedAt = LocalDateTime.now();
    }
}
