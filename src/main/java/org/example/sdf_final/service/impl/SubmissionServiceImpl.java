package org.example.sdf_final.service.impl;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.dto.response.SubmissionResponse;
import org.example.sdf_final.entity.Assignment;
import org.example.sdf_final.entity.Submission;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.mapper.SubmissionMapper;
import org.example.sdf_final.repository.AssignmentRepository;
import org.example.sdf_final.repository.SubmissionRepository;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final SubmissionMapper submissionMapper;

    @Override
    @Transactional
    public SubmissionResponse createSubmission(SubmissionRequest request) {
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Submission submission = submissionMapper.toEntity(request);
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setSubmittedAt(LocalDateTime.now()); //AutoDate (for check time of submission)

        return submissionMapper.toResponse(submissionRepository.save(submission));
    }

    @Override
    @Transactional
    public SubmissionResponse setGrade(Long id, Double grade) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        if (grade < 0 || grade > 100) {
            throw new RuntimeException("Grade must be between 0 and 100");
        }

        submission.setGrade(grade);
        return submissionMapper.toResponse(submissionRepository.save(submission));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getAllSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.findAll().stream()
                .filter(s -> s.getAssignment().getId().equals(assignmentId))
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse getSubmissionById(Long id) {
        return submissionRepository.findById(id)
                .map(submissionMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}