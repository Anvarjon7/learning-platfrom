package com.example.aiacademy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class QuestionRequest {

    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("choices")
    private List<String> choices;
    @JsonProperty("correctAnswerIndex")
    private Integer correctAnswerIndex;
}
