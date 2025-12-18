package com.example.aiacademy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ModuleResponse {

    private Long id;
    private String title;
    private String description;
    private List<LessonResponse> lessons;
}
