package org.example.edutrackerteach.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.repository.StudentsTaskRepository;
import org.example.edutrackerteach.service.StudentsTaskService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsTaskServiceImpl implements StudentsTaskService {
    private final StudentsTaskRepository studentsTaskRepository;
    @Override
    public long getAllByStudentId(Long studentId) {
        return studentsTaskRepository.countByStudentsId(studentId);
    }
    @Override
    public long getAllDoneTaskByStudentId(Long studentId) {
        //TODO доробити ці методи
//        return studentsTaskRepository.countByStatusGrantedOrEvaluated(studentId);
        return 0;
    }
    @Override
    public long getAllNotDoneTaskByStudentId(Long studentId) {
//        return studentsTaskRepository.countByStatusIsInProcess(studentId);
        return 0;
    }
}
