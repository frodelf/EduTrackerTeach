package org.example.edutrackerteach.service;

public interface StudentsTaskService {
    long getAllByStudentId(Long studentId);
    long getAllDoneTaskByStudentId(Long studentId);
    long getAllNotDoneTaskByStudentId(Long studentId);
}