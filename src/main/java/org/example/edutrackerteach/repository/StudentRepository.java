package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}