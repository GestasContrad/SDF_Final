package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.dto.response.SubmissionResponse;
import org.example.sdf_final.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(@Valid @RequestBody SubmissionRequest request) {
        return ResponseEntity.ok(submissionService.createSubmission(request));
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<SubmissionResponse> gradeSubmission(
            @PathVariable Long id,
            @RequestParam Double grade) {
        return ResponseEntity.ok(submissionService.setGrade(id, grade));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(submissionService.getAllSubmissionsByAssignment(assignmentId));
    }
}