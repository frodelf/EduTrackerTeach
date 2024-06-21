package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.repository.LessonRepository;
import org.example.edutrackerteach.service.LessonService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    @Override
    public long countByCourseId(long courseId) {
        return lessonRepository.countByCourseId(courseId);
    }
}