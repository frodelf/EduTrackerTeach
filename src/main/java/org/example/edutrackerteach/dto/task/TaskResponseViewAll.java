package org.example.edutrackerteach.dto.task;

import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusTask;

import java.util.Map;

@Data
public class TaskResponseViewAll {
    private Long id;
    private String name;
    private Map<String, String> course;
    private StatusTask status;
}