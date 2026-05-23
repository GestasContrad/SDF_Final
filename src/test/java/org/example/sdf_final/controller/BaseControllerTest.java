package org.example.sdf_final.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sdf_final.dto.request.LessonRequest;
import org.example.sdf_final.entity.*;
import org.example.sdf_final.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

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

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

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

    protected User createStudent() {
        User student = new User();
        student.setEmail("student_" + java.util.UUID.randomUUID() + "@mail.com");
        student.setPassword("password");
        student.setFirstName("Student");
        student.setLastName("Green");
        student.setRole(User.Role.STUDENT);
        return userRepository.save(student);
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

    protected Assignment createAssignment() {
        Assignment assignment = new Assignment();
        assignment.setTitle("Assignment 1");
        assignment.setDescription("Assignment Description");
        assignment.setCourse(createCourse());
        assignment.setLesson(createLesson());
        return assignmentRepository.save(assignment);
    }

    protected Submission createSubmission() {
        Submission submission = new Submission();
        submission.setStudent(createStudent());
        submission.setContent("SubmissionContent");
        submission.setAssignment(createAssignment());
        submission.setSubmittedAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

}