package org.example.edutrackerteach.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseRequestAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.example.edutrackerteach.entity.UserDetailsImpl;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.LessonService;
import org.example.edutrackerteach.service.StudentService;
import org.example.edutrackerteach.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final LessonService lessonService;
    private final TaskService taskService;
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("course/index");
    }
    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("course/edit");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(!courseService.isCourseAssignedToProfessor(userDetails.getProfessor().getId(), id))throw new AccessDeniedException("You don't have access to this page");
        return new ModelAndView("course/edit", "course", courseService.getById(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<CourseResponseViewAll>> getAll(@RequestParam int page, @RequestParam int pageSize, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(courseService.getAll(page, pageSize, userDetails.getProfessor().getId()));
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@ModelAttribute @Valid CourseRequestAdd courseRequestAdd, @AuthenticationPrincipal UserDetailsImpl userDetails) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.ok(courseService.add(courseRequestAdd, userDetails.getProfessor()));
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(!courseService.isCourseAssignedToProfessor(userDetails.getProfessor().getId(), id))throw new AccessDeniedException("You don't have access to this page");
        courseService.removeById(id);
        return ResponseEntity.ok("removed");
    }
    @GetMapping("/statistic")
    public ResponseEntity<Map<String, String>> statistic(@RequestParam long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(!courseService.isCourseAssignedToProfessor(userDetails.getProfessor().getId(), id))throw new AccessDeniedException("You don't have access to this page");
        Map<String, String> statistic = new HashMap<>();
        Course course = courseService.getById(id);

        if(course.getStudents() != null)statistic.put("students", String.valueOf(course.getStudents().size()));
        else statistic.put("students", "0");
        if(course.getLiteratures() != null)statistic.put("literatures", String.valueOf(course.getLiteratures().size()));
        else statistic.put("literatures", "0");
        statistic.put("lessons", String.valueOf(lessonService.countByCourseId(course.getId())));
        statistic.put("allTasks", String.valueOf(taskService.countAllTasksByCourseId(course.getId())));
        statistic.put("openTasks", String.valueOf(taskService.countAllOpenTasksByCourseId(course.getId())));
        statistic.put("closeTasks", String.valueOf(taskService.countAllCloseTasksByCourseId(course.getId())));

        return ResponseEntity.ok(statistic);
    }
    @GetMapping("/get-for-select")
    public ResponseEntity<Map<String, String>> getForSelect(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Map<String, String> forSelect = new HashMap<>();
        for (Course course : userDetails.getProfessor().getCourses()) {
            forSelect.put(course.getId().toString(), course.getName());
        }
        return ResponseEntity.ok(forSelect);
    }
    @GetMapping("/get-for-select-by-student/{studentId}")
    public ResponseEntity<Map<String, String>> getForSelect(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long studentId){
        Map<String, String> forSelect = new HashMap<>();
        Student student = studentService.getById(studentId);
        for (Course course : userDetails.getProfessor().getCourses()) {
            if(student.getCourses().stream().anyMatch(courseStudent -> courseStudent.getId().equals(course.getId())))
                forSelect.put(course.getId().toString(), course.getName());
        }
        return ResponseEntity.ok(forSelect);
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("courseActive", true);
    }
}