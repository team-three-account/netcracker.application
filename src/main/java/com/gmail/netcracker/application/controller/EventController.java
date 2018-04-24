package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventDao eventDao;

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/account/eventlist", method = RequestMethod.GET)
    public ModelAndView eventList(ModelAndView modelAndView) {
        List<Event> eventList = eventDao.eventList();
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("event/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "account/eventList/createNewEvent", method = RequestMethod.GET)
    public ModelAndView createNewEvent() {
        Event event = new Event();
        ModelAndView modelAndView = new ModelAndView("event/createnewevent", "createNewEvent", event);
        modelAndView.addObject("edit", false);
        return modelAndView;
    }

    @RequestMapping(value = "account/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@Valid Event event, BindingResult result) {
        if (result.hasErrors()) {
            return createNewEvent();
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/account/eventlist");
        eventService.createEventWithAuthUser(event);
        return modelAndView;
    }

    @RequestMapping(value = {"account/eventList/deleteEvent-{eventId}"}, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable int eventId) {
        eventDao.delete(eventId);
        return "redirect:/account/eventList";
    }

    @ModelAttribute("eventTypes")
    public List<Event> findAllEventTypes() {
        return eventDao.findAllEventTypes();
    }
}
