package org.example.edutrackerteach.mapper;

import org.example.edutrackerteach.dto.task.TaskResponseViewAll;
import org.example.edutrackerteach.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.stream.Collectors;

public class TaskMapper {
    public Page<TaskResponseViewAll> toDtoListForView(Page<Task> tasks) {
        return new PageImpl<>(
                tasks.getContent().stream()
                        .map(task -> toDtoForView(task))
                        .collect(Collectors.toList()),
                tasks.getPageable(),
                tasks.getTotalElements()
        );
    }
    private TaskResponseViewAll toDtoForView(Task task) {
        TaskResponseViewAll taskResponseViewAll = new TaskResponseViewAll();
        taskResponseViewAll.setId(task.getId());
        taskResponseViewAll.setName(task.getName());
        taskResponseViewAll.setStatus(task.getStatus());
        taskResponseViewAll.setCourse(Collections.singletonMap(task.getCourse().getId().toString(), task.getCourse().getName()));
        return taskResponseViewAll;
    }
}
