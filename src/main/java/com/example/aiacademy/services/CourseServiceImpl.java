package com.example.aiacademy.services;

import com.example.aiacademy.dto.CourseRequest;
import com.example.aiacademy.dto.CourseResponse;
import com.example.aiacademy.models.Course;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.CourseRepository;
import com.example.aiacademy.repositories.UserRepository;
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

    private void validateTutor(User user){
        boolean isTutor = user.getRoles().stream()
                .anyMatch(r -> r.getName().name().equals("ROLE_TUTOR") || r.getName().name().equals("ROLE_ADMIN"));

        if (!isTutor){
            throw new RuntimeException("Only tutors or admins can manage courses");
        }
    }

    @Override
    public CourseResponse createCourse(String tutorEmail, CourseRequest request) {
        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseThrow(() -> new RuntimeException("Tutor not found"));

        validateTutor(tutor);

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

        return toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::toResponse)
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

        return toResponse(course);
    }

    @Override
    public void deleteCourse(Long id, String tutorEmail) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getTutor().getEmail().equals(tutorEmail)){
            throw new RuntimeException("Unauthorized");
        }

        courseRepository.delete(course);
    }

    private CourseResponse toResponse(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory())
                .level(course.getLevel())
                .tutorEmail(course.getTutor().getEmail())
                .build();
    }
}
