package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.repository.ReviewRepository;
import org.example.edutrackerteach.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    @Override
    public long countAllVisitedLessonByStudentIdAndCourseId(Long studentId, Long courseId) {
        return reviewRepository.countByStudentIdAndCourseId(studentId, courseId);
    }
    @Override
    public long countAllVisitedLessonByStudentId(Long studentId) {
        return reviewRepository.countByStudentId(studentId);
    }
}