package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.AssignmentRequest;
import org.example.sdf_final.dto.response.AssignmentResponse;

import java.util.List;

public interface AssignmentService {

    AssignmentResponse createAssignment(AssignmentRequest request);

    AssignmentResponse getAssignmentById(Long id);

    List<AssignmentResponse> getAllAssignments();

    AssignmentResponse updateAssignment(Long id, AssignmentRequest request);

    void deleteAssignment(Long id);
}
