package com.example.aiacademy.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonResponse {

    private Object lesson;
    private String type;
}
