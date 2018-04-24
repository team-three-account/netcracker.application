package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.service.interfaces.EventService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account/calendar")
public class CalendarController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView findAll(ModelAndView modelAndView){
        Gson gson = new Gson();
        String eventList = gson.toJson(eventService.findAll());
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }
}
