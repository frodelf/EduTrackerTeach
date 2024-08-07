package org.example.edutrackerteach.mapper;

import org.example.edutrackerteach.dto.task.TaskRequestForAdd;
import org.example.edutrackerteach.dto.task.TaskResponseForAdd;
import org.example.edutrackerteach.dto.task.TaskResponseForView;
import org.example.edutrackerteach.dto.task.TaskResponseViewAll;
import org.example.edutrackerteach.entity.Task;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.stream.Collectors;

public class TaskMapper {
    public Page<TaskResponseViewAll> toDtoListForViewAll(Page<Task> tasks) {
        return new PageImpl<>(
                tasks.getContent().stream()
                        .map(this::toDtoForViewAll)
                        .collect(Collectors.toList()),
                tasks.getPageable(),
                tasks.getTotalElements()
        );
    }
    private TaskResponseViewAll toDtoForViewAll(Task task) {
        TaskResponseViewAll taskResponseViewAll = new TaskResponseViewAll();
        taskResponseViewAll.setId(task.getId());
        taskResponseViewAll.setName(task.getName());
        taskResponseViewAll.setStatus(task.getStatus());
        taskResponseViewAll.setCourse(Collections.singletonMap(task.getCourse().getId().toString(), task.getCourse().getName()));
        return taskResponseViewAll;
    }
    public TaskResponseForAdd toDtoForAdd(Task task) {
        TaskResponseForAdd taskResponseForAdd = new TaskResponseForAdd();
        taskResponseForAdd.setId(task.getId());
        taskResponseForAdd.setName(task.getName());
        taskResponseForAdd.setCourseId(task.getCourse().getId());
        taskResponseForAdd.setCourseName(task.getCourse().getName());
        return taskResponseForAdd;
    }
    public Task toEntityForAdd(TaskRequestForAdd taskRequestForAdd, TaskService taskService, CourseService courseService) {
        Task task = new Task();
        if(taskRequestForAdd.getId() != null)task = taskService.getById(taskRequestForAdd.getId());
        task.setName(taskRequestForAdd.getName());
        task.setCourse(courseService.getById(taskRequestForAdd.getCourseId()));
        return task;
    }
    public TaskResponseForView toDtoForViewPage(Task task) {
        TaskResponseForView taskResponseForView = new TaskResponseForView();
        taskResponseForView.setId(task.getId());
        taskResponseForView.setName(task.getName());
        taskResponseForView.setFile(task.getFile());
        if(task.getCourse()!=null)taskResponseForView.setCourse(Collections.singletonMap(task.getCourse().getId().toString(), task.getCourse().getName()));
        return taskResponseForView;
    }
}
