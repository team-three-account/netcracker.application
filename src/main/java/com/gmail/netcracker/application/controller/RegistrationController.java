package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dao.PersonDao;
import com.gmail.netcracker.application.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class RegistrationController {
    @Autowired
    public PersonDao personDao;
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(ModelAndView modelAndView) {
        modelAndView.addObject("person", new Person());
        return "index-register";
    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public String haveRegister(ModelAndView modelAndView, @ModelAttribute("person") Person person) {
        personDao.register(person);
        return "redirect:/";
    }
}
