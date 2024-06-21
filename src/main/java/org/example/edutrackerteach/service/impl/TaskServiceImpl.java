package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.service.TaskService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Override
    public long countAllTasksByCourseId(long courseId) {
        return 0;
    }
    @Override
    public long countAllOpenTasksByCourseId(long courseId) {
        return 0;
    }
    @Override
    public long countAllCloseTasksByCourseId(long courseId) {
        return 0;
    }
}
