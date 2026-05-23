package org.example.sdf_final.controller;

import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.entity.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LessonControllerTest extends BaseControllerTest {

    // Create lesson - success (role - teacher)
    @Test
    @WithMockUser(roles = "TEACHER")
    void createLesson_shouldReturn200() throws Exception {
        LessonRequest request = new LessonRequest();
        request.setTitle("Lesson 1");
        request.setCourseId(createCourse().getId());

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Get lesson - success
    @Test
    @WithMockUser
    void getLesson_shouldReturn200() throws Exception {
        Lesson lesson = createLesson();
        Long generatedId = lesson.getId();

        mockMvc.perform(get("/api/lessons/" + generatedId ))
                .andExpect(status().isOk());
    }

    // Create lesson - error (incorrect role)
    @Test
    @WithMockUser(roles = "STUDENT")
    void createLesson_student_shouldReturn403() throws Exception {

        LessonRequest request = new LessonRequest();
        request.setTitle("Lesson");

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
