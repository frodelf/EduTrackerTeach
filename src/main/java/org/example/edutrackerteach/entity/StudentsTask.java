package org.example.edutrackerteach.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusStudentsTask;

@Data
@Entity
public class StudentsTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double mark;
    @Column(length = 100)
    private String myWork;
    private StatusStudentsTask status;
    @OneToOne
    private Student student;
    @OneToOne
    private Task task;
}