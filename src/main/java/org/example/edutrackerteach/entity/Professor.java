package org.example.edutrackerteach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Professor extends User{
    @Column(length = 100)
    private String degree;
    @OneToMany(mappedBy = "professor")
    private List<Course> courses;
}