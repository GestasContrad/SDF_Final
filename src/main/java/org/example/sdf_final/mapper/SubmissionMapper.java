package org.example.sdf_final.mapper;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.dto.response.SubmissionResponse;
import org.example.sdf_final.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    Submission toEntity(SubmissionRequest request);

    @Mapping(source = "assignment.id", target = "assignmentId")
    @Mapping(source = "student.id", target = "studentId")
    SubmissionResponse toResponse(Submission submission);
}
