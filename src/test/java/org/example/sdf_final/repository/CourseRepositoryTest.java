package org.example.sdf_final.repository;

import org.example.sdf_final.entity.Course;
import org.example.sdf_final.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

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

    // Save course
    @Test
    void saveCourse_ShouldPersistData() {
        Course course = createCourse();

        assertNotNull(course.getId());
        assertEquals("Math", course.getTitle());
    }
}

