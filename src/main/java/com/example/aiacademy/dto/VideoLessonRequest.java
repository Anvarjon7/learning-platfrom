package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VideoLessonRequest {

    private String title;
    private String description;
    private Integer durationSeconds;
}
