package org.example.edutrackerteach.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String image;
    private Double maximumMark;
    @Column(length = 1000)
    private String goal;
    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    private Professor professor;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonManagedReference
    private List<Student> students;
    @OneToMany
    private List<Literature> literatures;
    @OneToMany
    private List<Task> tasks;
}