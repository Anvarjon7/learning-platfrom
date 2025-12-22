package com.example.aiacademy.services;

import com.example.aiacademy.dto.PdfLessonRequest;
import com.example.aiacademy.dto.PdfLessonResponse;
import com.example.aiacademy.models.Lesson;
import com.example.aiacademy.models.Module;
import com.example.aiacademy.models.PdfLesson;
import com.example.aiacademy.models.User;
import com.example.aiacademy.repositories.ModuleRepository;
import com.example.aiacademy.repositories.PdfLessonRepository;
import com.example.aiacademy.repositories.UserRepository;
import com.example.aiacademy.security.annotations.TutorOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PdfLessonServiceImpl implements PdfLessonService{

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final PdfLessonRepository pdfLessonRepository;
    private final LocalFileStorageService fileStorageService;

    @TutorOnly
    @Override
    public PdfLessonResponse create(Long moduleId, String tutorEmail, PdfLessonRequest request) {

        User tutor = userRepository.findByEmail(tutorEmail)
                .orElseThrow(() -> new RuntimeException("Tutor not found"));


        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        PdfLesson pdfLesson = new PdfLesson();
        pdfLesson.setModule(module);
        pdfLesson.setLessonType(Lesson.LessonType.PDF);
        pdfLesson.setTitle(request.getTitle());
        pdfLesson.setDescription(request.getDescription());
        pdfLesson.setNumberOfPages(request.getNumberOfPages());

        pdfLessonRepository.save(pdfLesson);

        return PdfLessonResponse.fromEntity(pdfLesson);
    }

    @TutorOnly
    @Override
    public PdfLessonResponse uploadPdf(Long lessonId, String tutorEmail, MultipartFile file) {

        PdfLesson pdfLesson = pdfLessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        String folder = "course-" + pdfLesson.getModule().getCourse().getId()
                + "/module-" + pdfLesson.getModule().getId();

        String fileUrl = fileStorageService.storeFile(file,folder);

        pdfLesson.setPdfUrl(fileUrl);
        pdfLessonRepository.save(pdfLesson);

        return PdfLessonResponse.fromEntity(pdfLesson);
    }
}
