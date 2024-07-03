package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.ForSelect2Dto;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.entity.UserDetailsImpl;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.StudentService;
import org.example.edutrackerteach.service.StudentsTaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final StudentsTaskService studentsTaskService;
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("student/index");
    }
    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable Long id){
        return new ModelAndView("student/view", "student", studentService.getByIdForView(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<StudentResponseViewAll>> getAll(@ModelAttribute StudentRequestFilter studentRequestFilter, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Page<StudentResponseViewAll> res = studentService.getAllByCourseList(studentRequestFilter.getPage(), studentRequestFilter.getPageSize(), userDetails.getProfessor().getCourses(), studentRequestFilter);
        return ResponseEntity.ok(studentService.getAllByCourseList(studentRequestFilter.getPage(), studentRequestFilter.getPageSize(), userDetails.getProfessor().getCourses(), studentRequestFilter));
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam Long id){
        studentService.removeById(id);
        return ResponseEntity.ok("deleted");
    }
    @GetMapping("/get-group-for-select")
    public ResponseEntity<Page<Map<String, String>>> getGroupForSelect(@ModelAttribute ForSelect2Dto forSelect2Dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(studentService.getAllByGroupForSelect(forSelect2Dto));
    }
    @GetMapping("/statistic")
    public ResponseEntity<Map<String, String>> statistic(@RequestParam(required = false) Long courseId, @RequestParam Long studentId){
        Map<String, String> map = new HashMap<>();
        Student student = studentService.getById(studentId);
        if(nonNull(courseId)){
            Course course = courseService.getById(courseId);
            map.put("courses", course.getName());
            map.put("allTasks", String.valueOf(studentsTaskService.getAllByStudentId(studentId)));
            map.put("doneTasks", String.valueOf(studentsTaskService.getAllDoneTaskByStudentId(studentId)));
            map.put("notDoneTasks", String.valueOf(studentsTaskService.getAllNotDoneTaskByStudentId(studentId)));
            map.put("lessons", "6");
            map.put("mark", "30");
        }else {

        }
        map.put("withCourse", String.valueOf(nonNull(courseId)));
        return ResponseEntity.ok(map);
    }
}