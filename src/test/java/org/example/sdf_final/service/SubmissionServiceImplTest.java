package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.entity.Assignment;
import org.example.sdf_final.entity.Submission;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.mapper.SubmissionMapper;
import org.example.sdf_final.repository.AssignmentRepository;
import org.example.sdf_final.repository.SubmissionRepository;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.impl.SubmissionServiceImpl;
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
class SubmissionServiceImplTest {

    @Mock private SubmissionRepository submissionRepository;
    @Mock private UserRepository userRepository;
    @Mock private AssignmentRepository assignmentRepository;
    @Mock private SubmissionMapper submissionMapper;

    @InjectMocks private SubmissionServiceImpl submissionService;

    // Create submission - success
    @Test
    void createSubmission_Logic_SetsTimeAndStatus_Success() {
        SubmissionRequest request = new SubmissionRequest();
        request.setStudentId(1L); request.setAssignmentId(1L);
        Submission submission = new Submission();

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(new Assignment()));
        when(submissionMapper.toEntity(request)).thenReturn(submission);
        when(submissionRepository.save(any())).thenReturn(submission);

        submissionService.createSubmission(request);

        assertNotNull(submission.getSubmittedAt());
        verify(submissionRepository).save(submission);
    }

    // Set grade (it have limit) - success
    @Test
    void setGrade_Validation_ThrowsOnInvalidGrides() {
        Submission submission = new Submission();
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        assertThrows(RuntimeException.class, () -> submissionService.setGrade(1L, -5.0));
        assertThrows(RuntimeException.class, () -> submissionService.setGrade(1L, 101.0));

        verify(submissionRepository, never()).save(any());
    }
}
