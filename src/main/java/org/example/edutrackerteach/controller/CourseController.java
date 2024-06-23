package org.example.edutrackerteach.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseRequestAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.LessonService;
import org.example.edutrackerteach.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
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
    public ModelAndView edit(@PathVariable Integer id) {
        return new ModelAndView("course/edit", "course", courseService.getById(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<CourseResponseViewAll>> getAll(@RequestParam int page, @RequestParam int pageSize){
        return ResponseEntity.ok(courseService.getAll(page, pageSize));
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@ModelAttribute @Valid CourseRequestAdd courseRequestAdd) {
        return ResponseEntity.ok(courseService.add(courseRequestAdd));
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam long id) {
        courseService.removeById(id);
        return ResponseEntity.ok("removed");
    }
    @GetMapping("/statistic")
    public ResponseEntity<Map<String, String>> statistic(@RequestParam long id){
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

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("courseActive", true);
    }
}