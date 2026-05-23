package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubmissionControllerTest extends BaseControllerTest {

    // Create submission - success (role - student)
    @Test
    @WithMockUser(roles = "STUDENT")
    void createSubmission_shouldReturn200() throws Exception {
        SubmissionRequest request = new SubmissionRequest();
        request.setStudentId(1L);
        request.setAssignmentId(1L);

        mockMvc.perform(post("/api/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Grade submission - success (role - teacher)
    @Test
    @WithMockUser(roles = "TEACHER")
    void gradeSubmission_shouldReturn200() throws Exception {
        mockMvc.perform(patch("/api/submissions/1/grade")
                        .param("grade", "90"))
                .andExpect(status().isOk());
    }

    // Grade submission - error (incorrect role)
    @Test
    @WithMockUser(roles = "STUDENT")
    void gradeSubmission_student_shouldReturn403() throws Exception {

        mockMvc.perform(patch("/api/submissions/1/grade")
                        .param("grade", "90"))
                .andExpect(status().isForbidden());
    }
}