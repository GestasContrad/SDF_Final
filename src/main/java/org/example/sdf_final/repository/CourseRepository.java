package org.example.sdf_final.repository;
import org.example.sdf_final.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}