package org.example.edutrackerteach.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean present;
    @OneToOne
    private Student student;
    @OneToOne
    private Lesson lesson;
}