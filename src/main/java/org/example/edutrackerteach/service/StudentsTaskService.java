package org.example.edutrackerteach.service;

public interface StudentsTaskService {
    long countAllByStudentId(Long studentId);
    long countAllDoneTaskByStudentId(Long studentId);
    long countAllNotDoneTaskByStudentId(Long studentId);
    long countAllByStudentIdAndCourseId(Long studentId, Long courseId);
    long countAllDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId);
    long countAllNotDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId);
    long countMarkByStudentIdAndCourseId(Long studentId, Long courseId);
}