package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.utilites.EventSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
public class CalendarController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView findAll(ModelAndView modelAndView){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerializer())
                .create();
        String eventList = gson.toJson(eventService.eventList());
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }
}
