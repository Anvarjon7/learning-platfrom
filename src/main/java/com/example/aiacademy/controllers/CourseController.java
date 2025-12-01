package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.CourseRequest;
import com.example.aiacademy.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<?> createCourse(Principal principal, @RequestBody CourseRequest request){
        return ResponseEntity.ok(
                courseService.createCourse(principal.getName(), request)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(Principal principal, @PathVariable Long id, @RequestBody CourseRequest request){
        return ResponseEntity.ok(
                courseService.updateCourse(id,principal.getName(),request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(Principal principal, @PathVariable Long id){

        courseService.deleteCourse(id, principal.getName());

        return ResponseEntity.ok("Course deleted");
    }


}
