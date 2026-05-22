package org.example.sdf_final.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubmissionResponse {
    private Long id;
    private String content;
    private Double grade;
    private LocalDateTime submittedAt;
    private Long assignmentId;
    private Long studentId;
}