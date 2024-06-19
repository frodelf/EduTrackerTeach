package org.example.edutrackerteach.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.entity.Professor;
import org.example.edutrackerteach.repository.ProfessorRepository;
import org.example.edutrackerteach.service.ProfessorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    @Override
    @Transactional
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }
    @Override
    public long count() {
        return professorRepository.count();
    }
}
