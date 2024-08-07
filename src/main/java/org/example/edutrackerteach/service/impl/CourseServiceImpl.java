package org.example.edutrackerteach.service.impl;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseDtoForAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Professor;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.mapper.CourseMapper;
import org.example.edutrackerteach.repository.CourseRepository;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.example.edutrackerteach.service.ProfessorService;
import org.example.edutrackerteach.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final MinioService minioService;
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final CourseMapper courseMapper = new CourseMapper();
    @Override
    public Page<CourseResponseViewAll> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("id")));
        return new CourseMapper().toDtoListForView(courseRepository.findAllByProfessorId(professorService.getAuthProfessor().getId(), pageable), minioService);
    }
    @Override
    public void removeById(Long courseId) {
        isCourseAssignedToProfessor(courseId);
        courseRepository.deleteById(courseId);
    }
    @Override
    public Course getById(Long courseId) {
        isCourseAssignedToProfessor(courseId);
        return courseRepository.findById(courseId).orElseThrow(
                () -> new EntityNotFoundException("Course with id = "+courseId+" not found")
        );
    }
    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }
    @Override
    @Transactional
    public long add(CourseDtoForAdd courseDtoForAdd) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Professor professor = professorService.getAuthProfessor();
        if(courseDtoForAdd.getId()!=null)isCourseAssignedToProfessor(courseDtoForAdd.getId());
        Course course = courseMapper.toEntityForAdd(courseDtoForAdd, this);
        if(courseDtoForAdd.getImage() != null)course.setImage(minioService.putMultipartFile(courseDtoForAdd.getImage()));
        if(professor!=null)course.setProfessor(professor);
        return save(course).getId();
    }
    @Override
    public void isCourseAssignedToProfessor(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new EntityNotFoundException("Course with id = "+courseId+" not found")
        );
        if (course.getProfessor()==null  || !Objects.equals(course.getProfessor().getId(), professorService.getAuthProfessor().getId())) throw new AccessDeniedException("You don't have access to this page");
    }
    @Override
    public CourseDtoForAdd getByIdForAdd(Long courseId) {
        return courseMapper.toDtoForAdd(getById(courseId));
    }
    @Override
    public Map<String, String> getForSelect() {
        Map<String, String> forSelect = new HashMap<>();
        for (Course course : professorService.getAuthProfessor().getCourses()) {
            forSelect.put(course.getId().toString(), course.getName());
        }
        return forSelect;
    }
    @Override
    public Map<String, String> getForSelectByStudent(Long studentId) {
        Map<String, String> forSelect = new HashMap<>();
        Student student = studentService.getById(studentId);
        for (Course course : professorService.getAuthProfessor().getCourses()) {
            if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(course.getId())))
                forSelect.put(course.getId().toString(), course.getName());
        }
        return forSelect;
    }
    @Override
    @Transactional
    public void addStudentToCourse(Map<String, String> students, Long courseId) {
        Course course = getById(courseId);
        students.forEach((studentId, value) -> {
            Student student = studentService.getById(Long.parseLong(studentId));
            if(Boolean.parseBoolean(value)){
                if(student.getCourses().stream().noneMatch(courseStudent -> courseStudent.getId().equals(courseId)))
                    course.getStudents().add(student);
            }
            else {
                if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(courseId)))
                    course.getStudents().removeIf(studentEl -> studentEl.getId().equals(Long.parseLong(studentId)));
            }
            save(course);
        });
    }
    @Override
    @Transactional
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Course course = getById(courseId);
        Student student = studentService.getById(studentId);
        if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(courseId)))
            course.getStudents().removeIf(studentEl -> studentEl.getId().equals(studentId));
        save(course);
    }
}