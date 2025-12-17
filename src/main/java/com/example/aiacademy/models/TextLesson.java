package com.example.aiacademy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "text_lessons")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TextLesson extends Lesson{

    @Column(length = 10000)
    private String content;
}
