package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.AuthRequest;
import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.response.AuthResponse;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.security.JwtService;
import org.example.sdf_final.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private AuthServiceImpl authService;

    // Register user - success
    @Test
    void register_ShouldCreateUserAndReturnToken_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setRole(User.Role.STUDENT);

        when(Objects.requireNonNull(passwordEncoder.encode("password"))).thenReturn("encoded_pass");
        when(jwtService.generateToken("test@example.com")).thenReturn("mock_jwt_token");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("STUDENT", response.getRole());

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password");
    }

    // Login user - success
    @Test
    void login_ShouldAuthenticateAndReturnToken_Success() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(User.Role.TEACHER);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("test@example.com")).thenReturn("mock_jwt_token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("TEACHER", response.getRole());

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("test@example.com", "password")
        );
    }

    // Login user - error (not found)
    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        AuthRequest request = new AuthRequest();
        request.setEmail("unknown@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(request));
        verify(jwtService, never()).generateToken(any());
    }
}