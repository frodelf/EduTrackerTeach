package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.entity.enums.StatusStudentsTask;
import org.example.edutrackerteach.repository.StudentsTaskRepository;
import org.example.edutrackerteach.service.StudentsTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentsTaskServiceImpl implements StudentsTaskService {
    private final StudentsTaskRepository studentsTaskRepository;
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
}
