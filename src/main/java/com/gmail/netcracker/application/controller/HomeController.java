package com.gmail.netcracker.application.controller;

<<<<<<< HEAD
import com.gmail.netcracker.application.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

>>>>>>> refs/remotes/origin/master

@Controller
public class HomeController {


<<<<<<< HEAD
    @RequestMapping(value = {"", "/", "home"})
    public String home(ModelAndView modelAndView) {
        return "index";
    }
=======
    @RequestMapping(value = {"/","/index-register"},method = RequestMethod.GET)
    public String loginPage(){
        return "index-register";
    }

>>>>>>> refs/remotes/origin/master
}
