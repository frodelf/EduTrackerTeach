package org.example.edutrackerteach;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.entity.Professor;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.service.ProfessorService;
import org.example.edutrackerteach.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final ProfessorService professorService;
    private final StudentService studentService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(studentService.count()!=0){
            Student student = new Student();
            student.setLastName("Деркач");
            student.setName("Денис");
            student.setMiddleName("Денис");
            student.setEmail("student@gmail.com");
            student.setTelegram("@student_tg");
            student.setGroupName("ТР-12");
            studentService.save(student);
        }
        if(professorService.count()!=0){
            Professor professor = new Professor();
            professor.setLastName("Безпала");
            professor.setName("Ольга");
            professor.setMiddleName("Миколаївна");
            professor.setEmail("professor@gmail.com");
            professor.setTelegram("@professor_tg");
            professor.setDegree("Старший викладач");
            professorService.save(professor);
        }
    }
}