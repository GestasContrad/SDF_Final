package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.CourseRequest;
import org.example.sdf_final.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    void deleteCourse(Long id);
}
