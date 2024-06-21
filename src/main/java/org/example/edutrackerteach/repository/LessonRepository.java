package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    long countByCourseId(long courseId);
}