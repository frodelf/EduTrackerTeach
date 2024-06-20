package org.example.edutrackerteach.service;

import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.springframework.data.domain.Page;

public interface CourseService {
    Page<CourseResponseViewAll> getAll(int page, int pageSize);
    void removeById(long id);
}