package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
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
    @GetMapping({"", "/"})
    public ModelAndView index() {
        return new ModelAndView("task/index");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("taskActive", true);
    }
}