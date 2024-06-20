package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.mapper.CourseMapper;
import org.example.edutrackerteach.repository.CourseRepository;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final MinioService minioService;
    @Override
    public Page<CourseResponseViewAll> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return new CourseMapper().toDtoListForView(courseRepository.findAll(pageable), minioService);
    }
    @Override
    public void removeById(long id) {
        courseRepository.deleteById(id);
    }
}