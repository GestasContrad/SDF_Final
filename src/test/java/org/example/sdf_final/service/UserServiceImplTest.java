package org.example.sdf_final.service;

import org.example.sdf_final.dto.request.ChangePasswordRequest;
import org.example.sdf_final.dto.request.RegisterRequest;
import org.example.sdf_final.dto.response.UserResponse;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.mapper.UserMapper;
import org.example.sdf_final.repository.UserRepository;
import org.example.sdf_final.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserServiceImpl userService;

    // Create user - success
    @Test
    void createUser_FullCycle_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setPassword("123");
        User user = new User();
        UserResponse response = new UserResponse();

        when(userMapper.toEntity(request)).thenReturn(user);
        when(Objects.requireNonNull(passwordEncoder.encode("123"))).thenReturn("encoded_123");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.createUser(request);

        assertNotNull(result);
        verify(passwordEncoder).encode("123");
        verify(userRepository).save(user);
    }

    // Update user - error (password is null)
    @Test
    void updateUser_ShouldNotChangePassword_WhenPasswordIsNull() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("NewName");
        request.setPassword(null);

        User existingUser = new User();
        existingUser.setPassword("old_secret");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenReturn(existingUser);
        when(userMapper.toResponse(any())).thenReturn(new UserResponse());

        userService.updateUser(1L, request);

        assertEquals("old_secret", existingUser.getPassword());
        verify(passwordEncoder, never()).encode(any());
    }

    // Change password - success
    @Test
    void changePassword_Logic_Success() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("old");
        request.setNewPassword("new");
        User user = new User();
        user.setPassword("hashed_old");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old", "hashed_old")).thenReturn(true);
        when(Objects.requireNonNull(passwordEncoder.encode("new"))).thenReturn("hashed_new");

        userService.changePassword(1L, request);

        assertEquals("hashed_new", user.getPassword());
        verify(userRepository).save(user);
    }

    // Change password - error (old password is incorrect)
    @Test
    void changePassword_WrongOldPassword_ThrowsRuntimeException() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword("wrong_old");
        request.setNewPassword("new");
        User user = new User();
        user.setPassword("hashed_actual_old");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_old", "hashed_actual_old")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.changePassword(1L, request));
        verify(userRepository, never()).save(any());
    }
}