package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.SubmissionRequest;
import org.example.sdf_final.entity.Assignment;
import org.example.sdf_final.entity.Submission;
import org.example.sdf_final.entity.User;
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
        User student = createStudent();
        Assignment assignment = createAssignment();

        SubmissionRequest request = new SubmissionRequest();
        request.setContent("Submission content");
        request.setStudentId(student.getId());
        request.setAssignmentId(assignment.getId());

        mockMvc.perform(post("/api/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Grade submission - success (role - teacher)
    @Test
    @WithMockUser(roles = "TEACHER")
    void gradeSubmission_shouldReturn200() throws Exception {
        Submission submission = createSubmission();
        Long generatedId = submission.getId();

        mockMvc.perform(patch("/api/submissions/"+ generatedId +"/grade")
                        .param("grade", "90"))
                .andExpect(status().isOk());
    }

    // Grade submission - error (incorrect role)
    @Test
    @WithMockUser(roles = "STUDENT")
    void gradeSubmission_student_shouldReturn403() throws Exception {
        Submission submission = createSubmission();
        Long generatedId = submission.getId();

        mockMvc.perform(patch("/api/submissions/"+ generatedId +"/grade")
                        .param("grade", "90"))
                .andExpect(status().isNotFound());
    }
}