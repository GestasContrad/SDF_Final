package org.example.sdf_final.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    public enum Role {
        ADMIN,
        TEACHER,
        STUDENT
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //Courses taught by a teacher
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

    //Student's completed assignments
    @OneToMany(mappedBy = "student")
    private Set<Submission> submissions;
}

