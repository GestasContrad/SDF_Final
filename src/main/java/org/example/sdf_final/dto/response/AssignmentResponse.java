package org.example.sdf_final.dto.response;

import lombok.Data;

@Data
public class AssignmentResponse {
    private Long id;
    private String title;
    private String description;
    private Long lessonId;
    private Long courseId;
}