package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.CourseRequest;
import org.example.sdf_final.dto.response.CourseResponse;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.mapper.CourseMapper;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock private CourseRepository courseRepository;
    @Mock private CourseMapper courseMapper;
    @Mock private UserRepository userRepository;

    @InjectMocks private CourseServiceImpl courseService;

    // Get all courses - success
    @Test
    void getAllCourses_Scenario_Success() {
        List<Course> courses = List.of(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.toResponse(any())).thenReturn(new CourseResponse());

        List<CourseResponse> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository).findAll();
        verify(courseMapper, times(2)).toResponse(any());
    }

    // Create course - success
    @Test
    void createCourse_ShouldSetTeacher_Success() {
        CourseRequest request = new CourseRequest();
        request.setTeacherId(1L);
        User teacher = new User();
        teacher.setId(1L);
        Course course = new Course();

        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(courseMapper.toEntity(request)).thenReturn(course);
        when(courseRepository.save(any())).thenReturn(course);
        when(courseMapper.toResponse(any())).thenReturn(new CourseResponse());

        courseService.createCourse(request);

        assertEquals(teacher, course.getTeacher());
        verify(courseRepository).save(course);
    }

    // Create course - error (teacher doesn't exist)
    @Test
    void createCourse_TeacherNotFound_ThrowsRuntimeException() {
        CourseRequest request = new CourseRequest();
        request.setTeacherId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.createCourse(request));
        verify(courseRepository, never()).save(any());
    }

    // Get course by id - error (not found)
    @Test
    void getCourseById_NotFound_ThrowsRuntimeException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> courseService.getCourseById(99L));
    }
}