package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LessonRequest {

    private String title;
    private String description;
    private String contentType;
}
