package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.dto.response.LessonResponse;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.Lesson;
import org.example.sdf_final.mapper.LessonMapper;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.LessonRepository;
import org.example.sdf_final.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock private LessonRepository lessonRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private LessonMapper lessonMapper;
    @InjectMocks private LessonServiceImpl lessonService;

    // Create lesson - success
    @Test
    void createLesson_WithCourseBinding_Success() {
        LessonRequest request = new LessonRequest();
        request.setCourseId(1L);
        Course course = new Course();
        Lesson lesson = new Lesson();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(lessonMapper.toEntity(request)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);

        lessonService.createLesson(request);

        assertEquals(course, lesson.getCourse());
        verify(lessonRepository).save(lesson);
    }

    // Update lesson - success
    @Test
    void updateLesson_ShouldChangeCourse_WhenIdsAreDifferent() {
        LessonRequest request = new LessonRequest();
        request.setCourseId(2L);

        Course oldCourse = new Course(); oldCourse.setId(1L);
        Course newCourse = new Course(); newCourse.setId(2L);
        Lesson lesson = new Lesson(); lesson.setCourse(oldCourse);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(newCourse));
        when(lessonRepository.save(any())).thenReturn(lesson);
        when(lessonMapper.toResponse(any())).thenReturn(new LessonResponse());

        when(lessonMapper.toResponse(any())).thenReturn(new LessonResponse());
        lessonService.updateLesson(1L, request);

        assertEquals(2L, lesson.getCourse().getId());
        verify(courseRepository).findById(2L);
        verify(lessonRepository).save(lesson);
    }

    // Create lesson - error (course doesn't exist)
    @Test
    void createLesson_CourseNotFound_ThrowsRuntimeException() {
        LessonRequest request = new LessonRequest();
        request.setCourseId(99L);

        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lessonService.createLesson(request));
        verify(lessonRepository, never()).save(any());
    }
}
