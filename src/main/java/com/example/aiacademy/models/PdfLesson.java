package com.example.aiacademy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pdf_lessons")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class PdfLesson extends Lesson{

    private String pdfUrl;
    private Integer numberOfPages;
}
