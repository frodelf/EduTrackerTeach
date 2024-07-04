package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.ForSelect2Dto;
import org.example.edutrackerteach.dto.StudentResponseForAdd;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.dto.student.StudentResponseViewOnePage;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student save(Student student);
    long count();
    Student getById(long id);
    Page<StudentResponseViewAll> getAllByCourseList(int page, int pageSize, List<Course> courseList, StudentRequestFilter studentRequestFilter);
    Page<Map<String, String>> getAllByGroupForSelect(ForSelect2Dto forSelect2Dto);
    StudentResponseViewOnePage getByIdForView(Long id);
    List<StudentResponseForAdd> getAllByGroupAndCourse(String group, Long courseId);
    void addToCourse(Map<String, String> students, Long courseId);
    void deleteFromCourse(Long studentId, Long courseId);
}