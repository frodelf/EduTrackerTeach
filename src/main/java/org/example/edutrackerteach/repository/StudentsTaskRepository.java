package org.example.edutrackerteach.repository;

import org.example.edutrackerteach.entity.StudentsTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentsTaskRepository extends JpaRepository<StudentsTask, Long> {
//    @Query("SELECT COUNT(st) FROM students_task st WHERE st.status IN ('GRANTED', 'EVALUATED') AND st.student_id = :userId")
//    Long countByStatusGrantedOrEvaluated(@Param("userId") Long userId);
//    @Query("SELECT COUNT(st) FROM students_task st WHERE st.status = 'IN_PROCESS' AND st.student.id = :userId")
//    Long countByStatusIsInProcess(@Param("userId") Long userId);
    long countByStudentsId(Long studentId);
}