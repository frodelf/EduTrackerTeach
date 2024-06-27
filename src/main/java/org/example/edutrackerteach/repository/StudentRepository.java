package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Page<Student> findAllByCoursesIn(List<Course> courses, Pageable pageable);
}