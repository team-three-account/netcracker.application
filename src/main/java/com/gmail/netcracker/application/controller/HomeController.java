package com.gmail.netcracker.application.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class controller for start page.
 */

@Controller
public class HomeController {
    @RequestMapping(value = {"", "/", "home"})
    public String home(ModelAndView modelAndView) {
        return "index";
    }
}


