package com.example.aiacademy.dto;

import com.example.aiacademy.models.QuizLesson;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class QuizLessonResponse {

    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questions;
    private final String type = "QUIZ";

    public static QuizLessonResponse fromEntity(QuizLesson q) {
        return QuizLessonResponse.builder()
                .id(q.getId())
                .title(q.getTitle())
                .description(q.getDescription())
                .questions(q.getQuestions()
                        .stream()
                        .map(QuestionResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
