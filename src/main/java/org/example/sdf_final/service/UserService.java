package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.ChangePasswordRequest;
import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.request.UpdateProfileRequest;
import org.example.sdf_final.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(RegisterRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, RegisterRequest request);

    UserResponse updateProfile(Long id, UpdateProfileRequest request);
    void changePassword(Long id, ChangePasswordRequest request);
    void deleteUser(Long id);
}