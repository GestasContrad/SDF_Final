package org.example.sdf_final.service.impl;

import org.example.sdf_final.dto.request.CourseRequest;
import org.example.sdf_final.dto.response.CourseResponse;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.mapper.CourseMapper;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = courseMapper.toEntity(request);
        course.setTeacher(teacher);

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow();
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course = courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
