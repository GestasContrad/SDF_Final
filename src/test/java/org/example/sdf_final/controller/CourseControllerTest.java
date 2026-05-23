package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.CourseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest extends BaseControllerTest {

    // Create course - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void createCourse_shouldReturn200() throws Exception {
        CourseRequest request = new CourseRequest();
        request.setTitle("Math");
        request.setTeacherId(1L);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Create course - error (don't have auth)
    @Test
    void createCourse_withoutAuth_shouldReturn403() throws Exception {
        CourseRequest request = new CourseRequest();
        request.setTitle("Math");

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    // Get course - success
    @Test
    void getCourse_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk());
    }

    // Delete course - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCourse_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }

    // Delete course - error (incorrect role)
    @Test
    @WithMockUser(roles = "STUDENT")
    void deleteCourse_student_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isForbidden());
    }
}