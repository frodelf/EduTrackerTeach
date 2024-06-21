package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("auth/login");
    }
}