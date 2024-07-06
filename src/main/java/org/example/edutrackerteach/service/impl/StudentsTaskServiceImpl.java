package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.studentTask.StudentTaskRequestForFilter;
import org.example.edutrackerteach.dto.studentTask.StudentTaskResponseForViewAll;
import org.example.edutrackerteach.entity.StudentsTask;
import org.example.edutrackerteach.entity.enums.StatusStudentsTask;
import org.example.edutrackerteach.mapper.StudentTaskMapper;
import org.example.edutrackerteach.repository.StudentsTaskRepository;
import org.example.edutrackerteach.service.StudentsTaskService;
import org.example.edutrackerteach.specification.StudentsTaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentsTaskServiceImpl implements StudentsTaskService {
    private final StudentsTaskRepository studentsTaskRepository;
    private final StudentTaskMapper studentTaskMapper = new StudentTaskMapper();
    @Override
    public long countAllByStudentId(Long studentId) {
        return studentsTaskRepository.countByStudentId(studentId);
    }
    @Override
    public long countAllDoneTaskByStudentId(Long studentId) {
        return studentsTaskRepository.countByStatusesAndStudentId(studentId, List.of(StatusStudentsTask.EVALUATED, StatusStudentsTask.GRANTED));
    }
    @Override
    public long countAllNotDoneTaskByStudentId(Long studentId) {
        return studentsTaskRepository.countByStatusesAndStudentId(studentId, List.of(StatusStudentsTask.IN_PROCESS));
    }
    @Override
    public long countAllByStudentIdAndCourseId(Long studentId, Long courseId) {
        return studentsTaskRepository.countByStudentIdAndCourseId(studentId, courseId);
    }
    @Override
    public long countAllDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId) {
        return studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentId, courseId, List.of(StatusStudentsTask.EVALUATED, StatusStudentsTask.GRANTED));
    }
    @Override
    public long countAllNotDoneTaskByStudentIdAndCourseId(Long studentId, Long courseId) {
        return studentsTaskRepository.countByStatusesAndStudentIdAndCourseId(studentId, courseId, List.of(StatusStudentsTask.IN_PROCESS));
    }
    @Override
    public long countMarkByStudentIdAndCourseId(Long studentId, Long courseId) {
        return studentsTaskRepository.countMarkByStudentIdAndCourseId(studentId, courseId);
    }
    @Override
    public Page<StudentTaskResponseForViewAll> getAll(StudentTaskRequestForFilter studentTaskRequestForFilter) {
        Pageable pageable = PageRequest.of(studentTaskRequestForFilter.getPage(), studentTaskRequestForFilter.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Specification<StudentsTask> specification = new StudentsTaskSpecification(studentTaskRequestForFilter);
        return studentTaskMapper.toDtoListForViewAll(studentsTaskRepository.findAll(specification, pageable));
    }
}
