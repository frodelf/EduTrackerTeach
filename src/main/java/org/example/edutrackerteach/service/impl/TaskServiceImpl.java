package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.task.TaskRequestFilter;
import org.example.edutrackerteach.dto.task.TaskResponseViewAll;
import org.example.edutrackerteach.entity.Task;
import org.example.edutrackerteach.mapper.TaskMapper;
import org.example.edutrackerteach.repository.TaskRepository;
import org.example.edutrackerteach.service.TaskService;
import org.example.edutrackerteach.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = new TaskMapper();
    //TODO доробити методи
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
    @Override
    public Page<TaskResponseViewAll> getAll(TaskRequestFilter taskRequestFilter) {
        Pageable pageable = PageRequest.of(taskRequestFilter.getPage(), taskRequestFilter.getPageSize(), Sort.by(Sort.Order.desc("id")));
        TaskSpecification taskSpecification = new TaskSpecification(taskRequestFilter);
        return taskMapper.toDtoListForView(taskRepository.findAll(taskSpecification, pageable));
    }
    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
