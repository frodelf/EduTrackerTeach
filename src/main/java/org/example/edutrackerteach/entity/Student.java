package org.example.edutrackerteach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
    @Column(length = 100)
    private String groupName;
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}