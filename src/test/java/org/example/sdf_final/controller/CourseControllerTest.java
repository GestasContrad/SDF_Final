package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.CourseRequest;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Create user
        User teacher =  createTeacher();

        // Create course request
        CourseRequest request = new CourseRequest();
        request.setTitle("Math");
        request.setTeacherId(1L);
        request.setTeacherId(teacher.getId());

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
    @WithMockUser
    void getCourse_shouldReturn200() throws Exception {
        // Create course
        Course course = createCourse();
        Long generatedId = course.getId();

        mockMvc.perform(get("/api/courses/" +  generatedId))
                .andExpect(status().isOk());
    }

    // Delete course - success
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCourse_shouldReturn204() throws Exception {
        // Create course
        Course course = createCourse();
        Long generatedId = course.getId();

        mockMvc.perform(delete("/api/courses/" +  generatedId))
                .andExpect(status().isNoContent());
    }

    // Delete course - error (incorrect role)
    @Test
    @WithMockUser(roles = "STUDENT")
    void deleteCourse_student_shouldReturn403() throws Exception {
        // Create course
        Course course = createCourse();
        Long generatedId = course.getId();

        mockMvc.perform(delete("/api/courses/" + generatedId))
                .andExpect(status().isNotFound());
    }
}