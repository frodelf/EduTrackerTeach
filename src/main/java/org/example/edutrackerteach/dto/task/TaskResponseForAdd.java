package org.example.edutrackerteach.dto.task;

import lombok.Data;

@Data
public class TaskResponseForAdd {
    private Long id;
    private String name;
    private Long courseId;
    private String courseName;
}