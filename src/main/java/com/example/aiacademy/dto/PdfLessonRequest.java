package com.example.aiacademy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PdfLessonRequest {

    private String title;
    private String description;
    private Integer numberOfPages;
}
