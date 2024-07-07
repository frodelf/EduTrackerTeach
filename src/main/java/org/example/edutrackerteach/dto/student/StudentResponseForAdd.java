package org.example.edutrackerteach.dto.student;

import lombok.Data;

@Data
public class StudentResponseForAdd {
    private Long id;
    private String groupName;
    private String fullName;
    private Boolean present;
}