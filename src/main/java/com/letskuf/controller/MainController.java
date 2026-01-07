package com.letskuf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView home() {
        //System.out.println("test");
        return new ModelAndView("redirect:/retrieveTeam.do");
    }
}
