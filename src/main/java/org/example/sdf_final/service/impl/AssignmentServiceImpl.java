package org.example.sdf_final.service.impl;

import org.example.sdf_final.dto.request.AssignmentRequest;
import org.example.sdf_final.dto.response.AssignmentResponse;
import org.example.sdf_final.entity.Assignment;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.Lesson;
import org.example.sdf_final.mapper.AssignmentMapper;
import org.example.sdf_final.repository.AssignmentRepository;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.LessonRepository;
import org.example.sdf_final.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    @Transactional
    public AssignmentResponse createAssignment(AssignmentRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Assignment assignment = assignmentMapper.toEntity(request);
        assignment.setLesson(lesson);
        assignment.setCourse(course);

        return assignmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    @Override
    public AssignmentResponse getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .map(assignmentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    @Override
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AssignmentResponse updateAssignment(Long id, AssignmentRequest request) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());

        //Updating relations if id changed
        if (!assignment.getLesson().getId().equals(request.getLessonId())) {
            Lesson lesson = lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            assignment.setLesson(lesson);
        }

        if (!assignment.getCourse().getId().equals(request.getCourseId())) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            assignment.setCourse(course);
        }

        return assignmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    @Override
    @Transactional
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}