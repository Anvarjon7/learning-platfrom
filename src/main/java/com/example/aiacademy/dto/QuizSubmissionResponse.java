package com.example.aiacademy.dto;

public class QuizSubmissionResponse {

    private int score;      // 0..100
    private boolean passed;
    private int totalQuestions;

    public QuizSubmissionResponse() {}

    public QuizSubmissionResponse(int score, boolean passed, int totalQuestions) {
        this.score = score;
        this.passed = passed;
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}

