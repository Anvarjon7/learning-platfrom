package com.example.aiacademy.services;

import com.example.aiacademy.dto.CourseRequest;
import com.example.aiacademy.dto.CourseResponse;
import com.example.aiacademy.models.Course;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.CourseRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.security.annotations.AdminOnly;
import com.example.aiacademy.security.annotations.TutorOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @TutorOnly
    @Override
    public CourseResponse createCourse(String tutorEmail, CourseRequest request) {
        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseGet(() -> {
                    // MVP fallback: persist the user
                    User newUser = new User();
                    newUser.setEmail(tutorEmail);
                    newUser.setPassword("TEMP"); // placeholder
                    newUser.setFullname("TEMP");
                    return userRepository.save(newUser);
                });

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .level(request.getLevel())
                .tutor(tutor)
                .creationAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        courseRepository.save(course);

        return CourseResponse.fromEntity(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(CourseResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Course not found"));

    }

    @Override
    public CourseResponse updateCourse(Long id, String tutorEmail, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getTutor().getEmail().equals(tutorEmail)){
            throw new RuntimeException(("You are not the tutor of this course"));
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setLevel(request.getLevel());
        course.setUpdatedAt(LocalDateTime.now());

        courseRepository.save(course);

        return CourseResponse.fromEntity(course);
    }

    @AdminOnly
    @Override
    public void deleteCourse(Long id, String tutorEmail) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getTutor().getEmail().equals(tutorEmail)){
            throw new RuntimeException("Unauthorized");
        }

        courseRepository.delete(course);
    }


}
