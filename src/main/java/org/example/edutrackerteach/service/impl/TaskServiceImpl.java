package org.example.edutrackerteach.service.impl;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.task.*;
import org.example.edutrackerteach.entity.Task;
import org.example.edutrackerteach.mapper.TaskMapper;
import org.example.edutrackerteach.repository.TaskRepository;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.example.edutrackerteach.service.TaskService;
import org.example.edutrackerteach.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CourseService courseService;
    private final MinioService minioService;
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
        return taskMapper.toDtoListForViewAll(taskRepository.findAll(taskSpecification, pageable));
    }
    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
    @Override
    public Task getById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task with id = "+taskId+" not found")
        );
    }
    @Override
    public TaskResponseForAdd getByIdForAdd(Long taskId) {
        return taskMapper.toDtoForAdd(getById(taskId));
    }
    @Override
    @Transactional
    public void add(TaskRequestForAdd taskRequestForAdd) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Task task = taskMapper.toEntityForAdd(taskRequestForAdd, this, courseService);
        if(taskRequestForAdd.getFile() != null)task.setFile(minioService.putMultipartFile(taskRequestForAdd.getFile()));
        save(task);
    }
    @Override
    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }
    @Override
    public TaskResponseForView getByIdForView(Long id) {
        return taskMapper.toDtoForViewPage(getById(id));
    }
}
