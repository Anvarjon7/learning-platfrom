package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {

    private String title;
    private String description;
    private String category;
    private String level;
}
