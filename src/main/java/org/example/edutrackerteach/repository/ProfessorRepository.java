package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}