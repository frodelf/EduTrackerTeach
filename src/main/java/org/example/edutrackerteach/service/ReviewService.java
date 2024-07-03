package org.example.edutrackerteach.service;

public interface ReviewService {
    long countAllVisitedLessonByStudentIdAndCourseId(Long studentId, Long courseId);
    long countAllVisitedLessonByStudentId(Long studentId);
}