package org.example.sdf_final.dto.response;

import lombok.Data;

@Data
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Long courseId;
}