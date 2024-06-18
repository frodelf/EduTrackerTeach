package org.example.edutrackerteach.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class StatisticController {
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("statistic/index");
    }
}