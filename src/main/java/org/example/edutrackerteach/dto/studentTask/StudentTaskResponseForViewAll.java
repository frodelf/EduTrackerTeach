package org.example.edutrackerteach.dto.studentTask;

import lombok.Data;
import org.example.edutrackerteach.entity.enums.StatusStudentsTask;

@Data
public class StudentTaskResponseForViewAll {
    private Long id;
    private String groupName;
    private String fullName;
    private String telegram;
    private StatusStudentsTask status;
    private Double mark;
    private String myWork;
}