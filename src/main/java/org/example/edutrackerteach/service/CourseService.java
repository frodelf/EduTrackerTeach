package org.example.edutrackerteach.service;

import io.minio.errors.*;
import org.example.edutrackerteach.dto.course.CourseRequestAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Professor;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface CourseService {
    Page<CourseResponseViewAll> getAll(int page, int pageSize, long professorId);
    void removeById(long id);
    Course getById(long id);
    Course save(Course course);
    long add(CourseRequestAdd courseRequestAdd, Professor professor) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
    boolean isCourseAssignedToProfessor(long professorId, long courseId);
    CourseRequestAdd getByIdForAdd(Integer id);
}