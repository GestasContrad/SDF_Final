package org.example.sdf_final.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Lesson ID is required")
    private Long lessonId;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}