package com.example.aiacademy.dto;

import com.example.aiacademy.models.TextLesson;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TextLessonResponse {


    private final Long id;
    private final String title;
    private final String description;
    private final String content;
    private final String type = "TEXT";
    private String tutorEmail;

    public static TextLessonResponse fromEntity(TextLesson l) {
        return TextLessonResponse.builder()
                .id(l.getId())
                .title(l.getTitle())
                .description(l.getDescription())
                .content(l.getContent())
                .build();
    }
}
