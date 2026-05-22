package org.example.sdf_final.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    //Which course does the lesson belong to
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    //Tasks within the lesson
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Set<Assignment> assignments;
}
