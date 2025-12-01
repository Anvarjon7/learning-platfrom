package com.example.aiacademy.services;

import com.example.aiacademy.dto.CourseRequest;
import com.example.aiacademy.dto.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(String tutorEmail, CourseRequest request);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id, String tutorEmail, CourseRequest request);
    void deleteCourse(Long id, String tutorEmail);
}
