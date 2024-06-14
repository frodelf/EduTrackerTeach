package org.example.edutrackerteach.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Teach teach;
    @OneToMany
    private List<Literature> literatures;
    @OneToMany
    private List<Task> tasks;
}