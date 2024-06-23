package org.example.edutrackerteach.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.repository.StudentRepository;
import org.example.edutrackerteach.service.StudentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
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
}
