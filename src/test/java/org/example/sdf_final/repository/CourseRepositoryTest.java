package org.example.sdf_final.repository;

import org.example.sdf_final.entity.Course;
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

    // Save course
    @Test
    void saveCourse_ShouldPersistData() {

        Course course = new Course();
        course.setTitle("Math");

        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse.getId());
        assertEquals("Math", savedCourse.getTitle());
    }
}

