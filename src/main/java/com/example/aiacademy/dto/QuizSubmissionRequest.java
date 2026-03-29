package com.example.aiacademy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class QuizSubmissionRequest {

    @NotEmpty
    @Valid
    private List<QuizAnswerSubmissionRequest> answers;

    public List<QuizAnswerSubmissionRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizAnswerSubmissionRequest> answers) {
        this.answers = answers;
    }
}

