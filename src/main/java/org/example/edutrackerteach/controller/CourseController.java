package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("course/index");
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<CourseResponseViewAll>> getAll(@RequestParam int page, @RequestParam int pageSize){
        return ResponseEntity.ok(courseService.getAll(page, pageSize));
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam long id) {
        courseService.removeById(id);
        return ResponseEntity.ok("removed");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("courseActive", true);
    }
}