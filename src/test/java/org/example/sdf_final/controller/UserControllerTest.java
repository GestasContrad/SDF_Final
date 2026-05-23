package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.UpdateProfileRequest;
import org.example.sdf_final.entity.User;
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
    @WithMockUser
    void getUser_shouldReturn200() throws Exception {
        User teacher = createTeacher();
        Long generatedId = teacher.getId();
        mockMvc.perform(get("/api/users/profile/" +  generatedId))
                .andExpect(status().isOk());
    }

    // Update profile - success
    @Test
    @WithMockUser
    void updateProfile_shouldReturn200() throws Exception {
        User teacher = createTeacher();
        Long generatedId = teacher.getId();

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setFirstName("NewName");
        request.setLastName(teacher.getLastName());
        request.setEmail(teacher.getEmail());

        mockMvc.perform(put("/api/users/profile/" + generatedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Get all users - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_shouldReturn200() throws Exception {
        User teacher = createTeacher();
        User student = createStudent();

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    // Delete user - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_shouldReturn204() throws Exception {
        User teacher = createTeacher();
        Long generatedId = teacher.getId();

        mockMvc.perform(delete("/api/users/" + generatedId))
                .andExpect(status().isNoContent());
    }
}