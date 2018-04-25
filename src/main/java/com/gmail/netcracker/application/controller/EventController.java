package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class EventController {
    @Autowired
    private EventDao eventDao;

    @Autowired
    EventService eventService;


    @RequestMapping(value = "/eventlist", method = RequestMethod.GET)
    public ModelAndView eventList(ModelAndView modelAndView) {
        List<Event> eventList = eventDao.eventList();
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("event/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.GET)
    public ModelAndView createNewEvent() {
        Event event = new Event();
        ModelAndView modelAndView = new ModelAndView("event/createnewevent", "createNewEvent", event);
        modelAndView.addObject("edit", false);
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@Valid Event event, BindingResult result,@RequestParam(value = "hidden") String hidden) {
        if (result.hasErrors()) {
            return createNewEvent();
        }

        
        ModelAndView modelAndView = new ModelAndView("redirect:/account/eventlist");
        eventService.insertEvent(event);
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/deleteEvent-{eventId}"}, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable int eventId) {
        eventDao.delete(eventId);
        return "redirect:/account/eventlist";
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable int eventId) {
        Event event = eventService.getEvent(eventId);
        ModelAndView modelAndView = new ModelAndView("event/testupdate", "editEvent", event);
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@Valid @ModelAttribute("editEventUpdate") Event event, BindingResult result,
                                    @PathVariable int eventId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/account/eventlist");
        if (result.hasErrors()) {
            return editEvent(eventId);
        }

        eventService.update(event);
        return modelAndView;
    }

    @ModelAttribute("eventTypes")
    public List<Event> findAllEventTypes() {
        return eventService.findAllEventTypes();
    }
}
