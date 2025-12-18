package com.example.aiacademy.dto;

import com.example.aiacademy.models.PdfLesson;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PdfLessonResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final Integer numberOfPages;
    private final String pdfUrl;
    private final String type = "PDF";

    public static PdfLessonResponse fromEntity(PdfLesson l) {
        return PdfLessonResponse.builder()
                .id(l.getId())
                .title(l.getTitle())
                .description(l.getDescription())
                .numberOfPages(l.getNumberOfPages())
                .pdfUrl(l.getPdfUrl())
                .build();
    }

}
