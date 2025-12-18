package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class QuizLessonRequest {

    private String title;
    private String description;
    private List<QuestionRequest> questions;
}
