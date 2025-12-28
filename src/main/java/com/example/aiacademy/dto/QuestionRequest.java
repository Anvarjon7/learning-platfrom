package com.example.aiacademy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class QuestionRequest {

    @JsonProperty("prompt")
    @NotBlank
    private String prompt;

    @NotEmpty
    @Size(min = 2)
    @JsonProperty("choices")
    private List<String> choices;

    @NotNull
    @Min(0)
    @JsonProperty("correctAnswerIndex")
    private Integer correctAnswerIndex;
}
