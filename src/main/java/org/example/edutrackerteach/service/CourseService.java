package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.course.CourseRequestAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {
    Page<CourseResponseViewAll> getAll(int page, int pageSize);
    void removeById(long id);
    Course getById(long id);
    long add(CourseRequestAdd courseRequestAdd);
}