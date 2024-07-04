package org.example.edutrackerteach.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusTask;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String file;
    private StatusTask status;
    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;
}