package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {

    LessonResponse createLesson(LessonRequest request);

    LessonResponse getLessonById(Long id);

    List<LessonResponse> getAllLessons();

    LessonResponse updateLesson(Long id, LessonRequest request);

    void deleteLesson(Long id);
}
