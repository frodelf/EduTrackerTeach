package org.example.edutrackerteach.dto.task;

import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusTask;

@Data
public class TaskRequestFilter {
    private Integer page;
    private Integer pageSize;
    private String name;
    private Long courseId;
    private StatusTask status;
}