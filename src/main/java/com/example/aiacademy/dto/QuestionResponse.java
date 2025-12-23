package com.example.aiacademy.dto;

import com.example.aiacademy.models.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class QuestionResponse {

    private Long id;
    private String prompt;
    private List<String> choices;
    private Integer correctAnswerIndex;

    public static QuestionResponse fromEntity(Question q) {
        return QuestionResponse.builder()
                .id(q.getId())
                .prompt(q.getPrompt())
                .choices(q.getChoices())
                .correctAnswerIndex(q.getCorrectAnswerIndex())
                .build();
    }
}
