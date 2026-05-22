package org.example.sdf_final.mapper;

import org.example.sdf_final.dto.request.AssignmentRequest;
import org.example.sdf_final.dto.response.AssignmentResponse;
import org.example.sdf_final.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    Assignment toEntity(AssignmentRequest request);

    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "course.id", target = "courseId")
    AssignmentResponse toResponse(Assignment assignment);
}
