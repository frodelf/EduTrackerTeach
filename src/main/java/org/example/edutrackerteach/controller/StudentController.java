package org.example.edutrackerteach.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.entity.UserDetailsImpl;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.example.edutrackerteach.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final MinioService minioService;
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("student/index");
    }
    @GetMapping("/get-all")
    @Transactional
    public ResponseEntity<Page<StudentResponseViewAll>> getAll(@ModelAttribute StudentRequestFilter studentRequestFilter, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Page<StudentResponseViewAll> res = studentService.getAllByCourseList(studentRequestFilter.getPage(), studentRequestFilter.getPageSize(), userDetails.getProfessor().getCourses(), studentRequestFilter);
        return ResponseEntity.ok(studentService.getAllByCourseList(studentRequestFilter.getPage(), studentRequestFilter.getPageSize(), userDetails.getProfessor().getCourses(), studentRequestFilter));
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam Long id){
        studentService.removeById(id);
        return ResponseEntity.ok("deleted");
    }
}