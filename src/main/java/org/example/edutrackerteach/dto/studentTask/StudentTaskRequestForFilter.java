package org.example.edutrackerteach.dto.studentTask;

import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusStudentsTask;

@Data
public class StudentTaskRequestForFilter {
    private int page;
    private int pageSize;
    private Long taskId;
    private String groupName;
    private String fullName;
    private String telegram;
    private StatusStudentsTask status;
}