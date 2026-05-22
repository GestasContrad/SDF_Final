package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.dto.response.SubmissionResponse;
import java.util.List;

public interface SubmissionService {

    SubmissionResponse createSubmission(SubmissionRequest request);
    SubmissionResponse getSubmissionById(Long id);
    List<SubmissionResponse> getAllSubmissions();
    SubmissionResponse setGrade(Long id, Double grade);
    List<SubmissionResponse> getAllSubmissionsByAssignment(Long assignmentId);

    void deleteSubmission(Long id);
}