package com.example.aiacademy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TextLessonRequest {

    private String title;
    private String description;
    private String content;
}
