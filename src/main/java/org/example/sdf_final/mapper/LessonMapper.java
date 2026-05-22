package org.example.sdf_final.mapper;

import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.dto.response.LessonResponse;
import org.example.sdf_final.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(LessonRequest request);

    @Mapping(source = "course.id", target = "courseId")
    LessonResponse toResponse(Lesson lesson);
}
