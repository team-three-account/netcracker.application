package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.validation.RegisterAndUpdateEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/account")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    RegisterAndUpdateEventValidator eventValidator;

    @RequestMapping(value = "/eventlist", method = RequestMethod.GET)
    public ModelAndView eventList(ModelAndView modelAndView) {
        List<Event> eventList = eventService.eventList();
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("event/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.GET)
    public ModelAndView createNewEvent() {
        Event event = new Event();
        ModelAndView modelAndView = new ModelAndView("event/createnewevent", "createNewEvent", event);

        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@ModelAttribute("createNewEvent")Event event, BindingResult result,@RequestParam(value = "hidden") String hidden) {
        ModelAndView modelAndView = new ModelAndView("event/createnewevent", "createNewEvent", event);
        eventValidator.validate(event,result);
        if (result.hasErrors()) {
            return modelAndView;
        }


        eventService.insertEvent(event);
        modelAndView.setViewName("redirect:/account/eventlist");
        return modelAndView;
    }


    @RequestMapping(value = {"/eventList/deleteEvent-{eventId}"}, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable int eventId) {
        eventService.delete(eventId);
        return "redirect:/account/eventlist";
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable int eventId) {
        Event event = eventService.getEvent(eventId);
        ModelAndView modelAndView = new ModelAndView("event/testupdate", "editEvent", event);
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@ModelAttribute("editEvent") Event event, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("event/testupdate", "editEvent", event);
        eventValidator.validate(event,result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        eventService.update(event);
        modelAndView.setViewName("redirect:/account/eventlist");
        return modelAndView;
    }

    @ModelAttribute("eventTypes")
    public List<Event> findAllEventTypes() {
        return eventService.findAllEventTypes();
    }
}
