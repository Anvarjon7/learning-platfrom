package com.example.aiacademy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class QuizAnswerSubmissionRequest {

    @NotNull
    @Min(1)
    private Long questionId;

    @NotNull
    @Min(0)
    private Integer selectedAnswerIndex;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public void setSelectedAnswerIndex(Integer selectedAnswerIndex) {
        this.selectedAnswerIndex = selectedAnswerIndex;
    }
}

