package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.task.TaskRequestFilter;
import org.example.edutrackerteach.dto.task.TaskResponseViewAll;
import org.springframework.data.domain.Page;

public interface TaskService {
    long countAllTasksByCourseId(long courseId);
    long countAllOpenTasksByCourseId(long courseId);
    long countAllCloseTasksByCourseId(long courseId);
    Page<TaskResponseViewAll> getAll(TaskRequestFilter taskRequestFilter);
    void deleteById(Long taskId);
}