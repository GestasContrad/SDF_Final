package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.UpdateProfileRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    // Get user - success
    @Test
    void getUser_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/profile/1"))
                .andExpect(status().isOk());
    }

    // Update profile - success
    @Test
    void updateProfile_shouldReturn200() throws Exception {
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setFirstName("NewName");

        mockMvc.perform(put("/api/users/profile/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Get all users - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_shouldReturn200() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    // Delete user - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_shouldReturn204() throws Exception {

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}