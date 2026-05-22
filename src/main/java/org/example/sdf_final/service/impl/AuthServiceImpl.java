package org.example.sdf_final.service.impl;

import org.example.sdf_final.dto.request.AuthRequest;
import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.response.AuthResponse;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        //Stabdart manager Spring Security

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ;

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}