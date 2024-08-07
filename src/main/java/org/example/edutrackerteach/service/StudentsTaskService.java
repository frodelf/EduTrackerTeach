package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.studentTask.StudentTaskRequestForFilter;
import org.example.edutrackerteach.dto.studentTask.StudentTaskResponseForViewAll;
import org.springframework.data.domain.Page;

public interface StudentsTaskService {
    long countAllByStudentId(Long studentId);
    long countAllDoneTaskByStudentId(Long studentId);
    long countAllNotDoneTaskByStudentId(Long studentId);
    long countAllByStudentIdAndCourseId(Long studentId, Long courseId);
    long countAllDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId);
    long countAllNotDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId);
    long countMarkByStudentIdAndCourseId(Long studentId, Long courseId);
    Page<StudentTaskResponseForViewAll> getAll(StudentTaskRequestForFilter studentTaskRequestForFilter);
}