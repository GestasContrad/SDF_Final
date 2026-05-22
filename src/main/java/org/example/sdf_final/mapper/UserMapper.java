package org.example.sdf_final.mapper;

import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.response.UserResponse;
import org.example.sdf_final.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);
}
