package org.example.sdf_final.service.impl;

import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.dto.response.LessonResponse;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.Lesson;
import org.example.sdf_final.mapper.LessonMapper;
import org.example.sdf_final.repository.CourseRepository; // Нужно добавить репозиторий курсов
import org.example.sdf_final.repository.LessonRepository;
import org.example.sdf_final.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository; // Добавляем зависимость
    private final LessonMapper lessonMapper;

    @Override
    @Transactional
    public LessonResponse createLesson(LessonRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));
        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setCourse(course);
        return lessonMapper.toResponse(lessonRepository.save(lesson));
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        return lessonRepository.findById(id)
                .map(lessonMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LessonResponse updateLesson(Long id, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());

        if (!lesson.getCourse().getId().equals(request.getCourseId())) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            lesson.setCourse(course);
        }

        return lessonMapper.toResponse(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}