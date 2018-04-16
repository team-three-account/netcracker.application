package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


    @RequestMapping(value = {"", "/", "home"})
    public String home(ModelAndView modelAndView) {
        return "index";
    }
}
