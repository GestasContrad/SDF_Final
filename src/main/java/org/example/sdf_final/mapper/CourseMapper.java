package org.example.sdf_final.mapper;

import org.example.sdf_final.dto.request.CourseRequest;
import org.example.sdf_final.dto.response.CourseResponse;
import org.example.sdf_final.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseRequest request);

    @Mapping(source = "teacher.id", target = "teacherId")
    CourseResponse toResponse(Course course);
}
