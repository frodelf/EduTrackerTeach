package org.example.edutrackerteach.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.ForSelect2Dto;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.mapper.StudentMapper;
import org.example.edutrackerteach.repository.StudentRepository;
import org.example.edutrackerteach.service.StudentService;
import org.example.edutrackerteach.specification.StudentSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = new StudentMapper();
    @Override
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    @Override
    public long count() {
        return studentRepository.count();
    }
    @Override
    public Student getById(long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id = "+id+" not found")
        );
    }
    @Override
    public Page<StudentResponseViewAll> getAllByCourseList(int page, int pageSize, List<Course> courseList, StudentRequestFilter studentRequestFilter) {
        if(!studentRequestFilter.getGroup().isBlank() || !studentRequestFilter.getFullName().isBlank() || !studentRequestFilter.getTelegram().isBlank() || !studentRequestFilter.getPhone().isBlank() || studentRequestFilter.getCourse() != null){
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
            Specification<Student> spec = new StudentSpecification(studentRequestFilter);
            return studentMapper.toDtoListForViewAll(studentRepository.findAll(spec, pageable));
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return studentMapper.toDtoListForViewAll(studentRepository.findAllByCoursesIn(courseList, pageable));
    }
    @Override
    public void removeById(Long id) {
        System.out.println(id);
    }
    @Override
    public Page<Map<String, String>> getAllByGroupForSelect(ForSelect2Dto forSelect2Dto) {
        Pageable pageable = PageRequest.of(forSelect2Dto.getPage(), forSelect2Dto.getSize(), Sort.by(Sort.Order.desc("id")));
        Page<String> groups = studentRepository.findAllGroupNamesByGroupNameLikeIgnoreCase(forSelect2Dto.getQuery(), pageable);
        List<Map<String, String>> list = new ArrayList<>();
        for (String group : groups.getContent()) {
            Map<String, String> map = new HashMap<>();
            map.put(group, group);
            list.add(map);
        }
        return new PageImpl<>(list, pageable, groups.getTotalElements());
    }
}