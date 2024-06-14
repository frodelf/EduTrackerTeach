package org.example.edutrackerteach.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class StudentsTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double mark;
    @Column(length = 100)
    private String myWork;
    private String status;
    @OneToOne
    private Student student;
    @OneToOne
    private Task task;
}