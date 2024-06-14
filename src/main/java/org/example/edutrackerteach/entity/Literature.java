package org.example.edutrackerteach.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Literature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String link;
}