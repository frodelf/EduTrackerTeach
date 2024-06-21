package org.example.edutrackerteach.service;

import org.example.edutrackerteach.entity.Professor;

public interface ProfessorService {
    Professor save(Professor professor);
    long count();
    Professor getByEmailForAuth(String username);
}