package com.example.aiacademy.dto;

import com.example.aiacademy.models.Course;
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

    public static CourseResponse fromEntity(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .level(course.getLevel())
                .tutorEmail(course.getTutor().getFullname())
                .build();
    }
}
