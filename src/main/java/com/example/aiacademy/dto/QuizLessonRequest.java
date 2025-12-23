package com.example.aiacademy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class QuizLessonRequest {

    public QuizLessonRequest() {
        System.out.println(">>> QuizLessonRequest CREATED");
    }

    private String title;
    private String description;

    @NotNull
    @NotEmpty
    @JsonProperty("questions")
    private List<QuestionRequest> questions;
}
