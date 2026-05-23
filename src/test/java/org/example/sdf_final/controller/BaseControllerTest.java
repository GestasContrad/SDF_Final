package org.example.sdf_final.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.Lesson;
import org.example.sdf_final.entity.User;
import org.example.sdf_final.repository.CourseRepository;
import org.example.sdf_final.repository.LessonRepository;
import org.example.sdf_final.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

// Base controller test, abstract class
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
    }

    protected User createTeacher() {
        User teacher = new User();
        // Make email unique
        teacher.setEmail("teacher_" + java.util.UUID.randomUUID() + "@mail.com");
        teacher.setPassword("password");
        teacher.setFirstName("Teacher");
        teacher.setLastName("Green");
        teacher.setRole(User.Role.TEACHER);
        return userRepository.save(teacher);
    }

    protected Course createCourse() {
        User teacher = createTeacher();

        Course course = new Course();
        course.setTitle("Math");
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    protected Lesson createLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Lesson" + java.util.UUID.randomUUID());
        lesson.setContent("LessonContent");
        lesson.setCourse(createCourse());
        return lessonRepository.save(lesson);
    }

}