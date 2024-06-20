package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}