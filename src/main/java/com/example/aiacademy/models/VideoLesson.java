package com.example.aiacademy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "video_lessons")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoLesson extends Lesson {

    private String videoUrl;
    private Integer durationSeconds;

    @Column(length = 2048)
    private String summary;
}
