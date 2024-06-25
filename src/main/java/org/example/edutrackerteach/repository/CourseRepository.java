package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByProfessorId(Long professorId, Pageable pageable);
}