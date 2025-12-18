package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class QuestionRequest {

    private String prompt;
    private List<String> choices;
    private Integer correctAnswerIndex;
}
