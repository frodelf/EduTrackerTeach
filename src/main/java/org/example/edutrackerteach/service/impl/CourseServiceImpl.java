package org.example.edutrackerteach.service.impl;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseRequestAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Professor;
import org.example.edutrackerteach.mapper.CourseMapper;
import org.example.edutrackerteach.repository.CourseRepository;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final MinioService minioService;
    private final CourseMapper courseMapper = new CourseMapper();
    @Override
    public Page<CourseResponseViewAll> getAll(int page, int pageSize, long professorId) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return new CourseMapper().toDtoListForView(courseRepository.findAllByProfessorId(professorId, pageable), minioService);
    }
    @Override
    public void removeById(long id) {
        courseRepository.deleteById(id);
    }
    @Override
    public Course getById(long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course with id = "+id+" not found")
        );
    }
    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }
    @Override
    @Transactional
    public long add(CourseRequestAdd courseRequestAdd, Professor professor) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Course course = courseMapper.toEntityForAdd(courseRequestAdd, this);
        if(courseRequestAdd.getImage() != null)course.setImage(minioService.putMultipartFile(courseRequestAdd.getImage()));
        if(professor!=null)course.setProfessor(professor);
        return save(course).getId();
    }
    @Override
    public boolean isCourseAssignedToProfessor(long professorId, long courseId) {
        return getById(courseId).getProfessor()!=null  &&  getById(courseId).getProfessor().getId() == professorId;
    }
}