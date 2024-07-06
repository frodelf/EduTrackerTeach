package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.studentTask.StudentTaskRequestForFilter;
import org.example.edutrackerteach.dto.studentTask.StudentTaskResponseForViewAll;
import org.example.edutrackerteach.service.StudentsTaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student-task")
public class StudentTaskController {
    private final StudentsTaskService studentsTaskService;
    @GetMapping("/get-all-by-task")
    public ResponseEntity<Page<StudentTaskResponseForViewAll>> getAllStudentByTask(@ModelAttribute StudentTaskRequestForFilter studentTaskRequestForFilter) {
        return ResponseEntity.ok(studentsTaskService.getAll(studentTaskRequestForFilter));
    }
}