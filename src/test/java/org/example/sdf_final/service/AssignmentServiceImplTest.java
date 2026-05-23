package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.AssignmentRequest;
import org.example.sdf_final.dto.response.AssignmentResponse;
import org.example.sdf_final.entity.Assignment;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.Lesson;
import org.example.sdf_final.mapper.AssignmentMapper;
import org.example.sdf_final.repository.AssignmentRepository;
import org.example.sdf_final.repository.LessonRepository;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.service.impl.AssignmentServiceImpl;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.BeforeEach;
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
class AssignmentServiceImplTest {

    @Mock private AssignmentRepository assignmentRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private AssignmentMapper assignmentMapper;

    @InjectMocks private AssignmentServiceImpl assignmentService;

    private Assignment assignment;
    private Lesson oldLesson;
    private Course oldCourse;

    @BeforeEach
    void setUp() {
        oldCourse = new Course();
        oldCourse.setId(1L);

        oldLesson = new Lesson();
        oldLesson.setId(1L);
        oldLesson.setCourse(oldCourse);

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setTitle("Old Title");
        assignment.setLesson(oldLesson);
        assignment.setCourse(oldCourse);
    }

    // Update Assignment - success
    @Test
    void updateAssignment_ShouldUpdateFieldsAndChangeRelations_Success() {
        Long newLessonId = 2L;
        Long newCourseId = 2L;

        Lesson newLesson = new Lesson(); newLesson.setId(newLessonId);
        Course newCourse = new Course(); newCourse.setId(newCourseId);

        AssignmentRequest updateReq = new AssignmentRequest();
        updateReq.setTitle("New Title");
        updateReq.setDescription("New Description");
        updateReq.setLessonId(newLessonId);
        updateReq.setCourseId(newCourseId);

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(lessonRepository.findById(newLessonId)).thenReturn(Optional.of(newLesson));
        when(courseRepository.findById(newCourseId)).thenReturn(Optional.of(newCourse));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);
        when(assignmentMapper.toResponse(any())).thenReturn(new AssignmentResponse());

        assignmentService.updateAssignment(1L, updateReq);

        assertEquals("New Title", assignment.getTitle());
        assertEquals(newLessonId, assignment.getLesson().getId());
        assertEquals(newCourseId, assignment.getCourse().getId());

        verify(assignmentRepository).findById(1L);
        verify(lessonRepository).findById(newLessonId);
        verify(courseRepository).findById(newCourseId);
        verify(assignmentRepository).save(assignment);
    }

    // Update assignment - error (course not found)
    @Test
    void updateAssignment_NotFound_ThrowsRuntimeException() {
        AssignmentRequest updateReq = new AssignmentRequest();
        when(assignmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> assignmentService.updateAssignment(99L, updateReq));
        verify(assignmentRepository, times(1)).findById(99L);
        verify(assignmentRepository, never()).save(any());
    }

    // Get assignment - error (not found)
    @Test
    void getAssignmentById_NotFound_ThrowsRuntimeException() {
        when(assignmentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> assignmentService.getAssignmentById(99L));
        verify(assignmentRepository).findById(99L);
    }
}
