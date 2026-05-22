package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.AuthRequest;
import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}