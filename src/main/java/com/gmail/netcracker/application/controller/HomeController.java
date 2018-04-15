package com.gmail.netcracker.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {


    @RequestMapping(value = {"/","/index-register"},method = RequestMethod.GET)
    public String loginPage(){
        return "index-register";
    }

}
