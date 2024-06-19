package org.example.edutrackerteach.service;

import org.example.edutrackerteach.entity.Student;

public interface StudentService {
    Student save(Student student);
    long count();
}