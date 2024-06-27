package org.example.edutrackerteach.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
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
    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Course> courses;
}