package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.CalendarService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FilterService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.Filter;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class CalendarController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private Gson gsonEvents;

    @RequestMapping(value = "/getEvents", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String calendarRange(@RequestParam("start") Long start,
                                @RequestParam("end") Long end,
                                @RequestParam("filterPriority") String jsonPriority,
                                @RequestParam("filterTypes") String jsonTypes){
        List<Event> eventList = calendarService.getEventsFromRange(userService.getAuthenticatedUser(), start, end);
        eventList = filterService.filterOfPriority(Arrays.asList(gsonEvents.fromJson(jsonPriority, Long[].class)), eventList);
        eventList = filterService.filterOfType(Arrays.asList(gsonEvents.fromJson(jsonTypes, Long[].class)), eventList);
        return gsonEvents.toJson(eventList);
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView calendarHome(ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("priorities", eventService.getAllPriorities());
        modelAndView.addObject("eventTypes", eventService.getAllEventTypes());
        modelAndView.addObject("filter", new Filter());
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView calendarWithFilter(@ModelAttribute("filter") Filter filter,
                                           BindingResult result,
                                           ModelAndView modelAndView) {

        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("priorities", eventService.getAllPriorities());
        modelAndView.addObject("eventTypes", eventService.getAllEventTypes());
        modelAndView.addObject("filter", filter);
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }
}
