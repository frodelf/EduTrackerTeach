package org.example.edutrackerteach.dto.student;

import lombok.Data;

@Data
public class StudentRequestFilter {
    private int page;
    private int pageSize;
    private String group;
    private String fullName;
    private Long course;
    private String telegram;
    private String phone;
}