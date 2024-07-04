package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.example.edutrackerteach.dto.task.TaskRequestFilter;
import org.example.edutrackerteach.dto.task.TaskResponseViewAll;
import org.example.edutrackerteach.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    @GetMapping({"", "/"})
    public ModelAndView index() {
        return new ModelAndView("task/index");
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<TaskResponseViewAll>> getAll(TaskRequestFilter taskRequestFilter){
        return ResponseEntity.ok(taskService.getAll(taskRequestFilter));
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("taskActive", true);
    }
}