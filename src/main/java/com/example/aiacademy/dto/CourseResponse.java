package com.example.aiacademy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String level;
    private String tutorEmail;
}
