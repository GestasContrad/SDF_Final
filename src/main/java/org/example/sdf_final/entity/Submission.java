package org.example.sdf_final.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //The student who passed the assignment
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    //What task does it relate to
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(length = 2000)
    private String content;

    @Column
    private Double grade;

    @Column(nullable = false)
    private LocalDateTime submittedAt;
}
