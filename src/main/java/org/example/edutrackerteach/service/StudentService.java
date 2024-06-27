package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    Student save(Student student);
    long count();
    Student getById(long id);
    Page<StudentResponseViewAll> getAllByCourseList(int page, int pageSize, List<Course> courseList, StudentRequestFilter studentRequestFilter);
    void removeById(Long id);
}