package org.example.edutrackerteach.dto.task;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusTask;

@Data
public class TaskRequestFilter {
    @Min(value = 0, message = "{error.field.min-value}")
    private Integer page;
    @Min(value = 1, message = "{error.field.min-value}")
    private Integer pageSize;
    private String name;
    private Long courseId;
    private StatusTask status;
}