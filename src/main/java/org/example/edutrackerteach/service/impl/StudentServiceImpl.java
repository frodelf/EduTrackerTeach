package org.example.edutrackerteach.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.ForSelect2Dto;
import org.example.edutrackerteach.dto.StudentResponseForAdd;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.dto.student.StudentResponseViewOnePage;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.mapper.StudentMapper;
import org.example.edutrackerteach.repository.StudentRepository;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.StudentService;
import org.example.edutrackerteach.specification.StudentSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
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
            Specification<Student> spec;
            if(nonNull(studentRequestFilter.getCourse())) spec = new StudentSpecification(studentRequestFilter);
            else spec = new StudentSpecification(studentRequestFilter, courseList);
            return studentMapper.toDtoListForViewAll(studentRepository.findAll(spec, pageable));
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return studentMapper.toDtoListForViewAll(studentRepository.findDistinctByCoursesIn(courseList, pageable));
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
    @Override
    public StudentResponseViewOnePage getByIdForView(Long id) {
        return studentMapper.toDtoForViewOnePage(getById(id));
    }
    @Override
    public List<StudentResponseForAdd> getAllByGroupAndCourse(String group, Long courseId) {
        return studentMapper.toDtoForAddList(studentRepository.findAllByGroupName(group), courseId);
    }
    @Override
    @Transactional
    public void addToCourse(Map<String, String> students, Long courseId) {
        Course course = courseService.getById(courseId);
        students.forEach((studentId, value) -> {
            Student student = getById(Long.parseLong(studentId));
            if(Boolean.parseBoolean(value)){
                if(student.getCourses().stream().noneMatch(courseStudent -> courseStudent.getId().equals(courseId)))
                    course.getStudents().add(student);
            }
            else {
                if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(courseId)))
                    course.getStudents().removeIf(studentEl -> studentEl.getId().equals(Long.parseLong(studentId)));
            }
            courseService.save(course);
        });
    }
    @Override
    public void deleteFromCourse(Long studentId, Long courseId) {
        Course course = courseService.getById(courseId);
        Student student = getById(studentId);
        if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(courseId)))
            course.getStudents().removeIf(studentEl -> studentEl.getId().equals(studentId));
        courseService.save(course);
    }
}