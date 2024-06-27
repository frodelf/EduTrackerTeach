package org.example.edutrackerteach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Course> courses;
}