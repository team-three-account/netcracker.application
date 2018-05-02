package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventType;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.RegisterAndUpdateEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/account")
public class EventController {

    private final EventService eventService;
    private final NoteService noteService;

    private final UserService userService;

    private final RegisterAndUpdateEventValidator eventValidator;

    @Autowired
    public EventController(EventService eventService, NoteService noteService, UserService userService,
                           RegisterAndUpdateEventValidator eventValidator) {
        this.eventService = eventService;
        this.noteService = noteService;
        this.userService = userService;
        this.eventValidator = eventValidator;
    }


    @RequestMapping(value = "/eventlist", method = RequestMethod.GET)
    public ModelAndView eventList(ModelAndView modelAndView) {
        User authUser = userService.getAuthenticatedUser();
        Long userId = authUser.getId();
        modelAndView.addObject("auth_user", authUser);
        modelAndView.addObject("publicEventList", eventService.findPublicEvents());
        modelAndView.addObject("privateEventList", eventService.findPrivateEvents(userId));
        modelAndView.addObject("friendsEventList", eventService.findFriendsEvents(userId));
        modelAndView.addObject("drafts", eventService.findDrafts(userId));
        modelAndView.addObject("noteList", noteService.noteList());
        modelAndView.setViewName("event/eventList");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.GET)
    public ModelAndView createNewEvent(@ModelAttribute(value = "createNewEvent") Event event, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/createNewEvent");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@ModelAttribute("createNewEvent") Event event,
                                     BindingResult result,
                                     @RequestParam(value = "hidden") String hidden,
                                     ModelAndView modelAndView) {
        //TODO fix problem with drafts (problem in jsp and controller)
        modelAndView.setViewName("event/createNewEvent");
        eventValidator.validate(event, result);
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

    @RequestMapping(value = "/eventList/event-{eventId}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("eventId") int eventId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("user_creator", userService.findUserById(eventService.getEvent(eventId).getCreator()));
        int participants = eventService.countParticipants(eventId);
        modelAndView.addObject("participants", participants );
        modelAndView.setViewName("event/viewEvent");
        return modelAndView;
    }


    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable int eventId, ModelAndView modelAndView) {
        modelAndView.addObject("editEvent", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/updateEvent");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@ModelAttribute("editEvent") Event event,
                                    BindingResult result,
                                    ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/updateEvent");
        eventValidator.validate(event, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        eventService.update(event);
        modelAndView.setViewName("redirect:/account/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "/participate", method = RequestMethod.POST)
    public String deleteFriend(@RequestParam(value = "event_id") String event_id) {
        eventService.participate(userService.getAuthenticatedUser().getId(), Long.parseLong(event_id));
        return "redirect:/account/myevents";
    }

    @RequestMapping(value = "/myevents", method = RequestMethod.GET)
    public ModelAndView getMyEvent(ModelAndView modelAndView) {
        modelAndView.addObject("personalEventList", eventService.getAllMyEvents());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/personalEvents");
        return modelAndView;
    }

    @ModelAttribute("eventTypes")
    public List<EventType> getAllEventTypes() {
        return eventService.getAllEventTypes();
    }

    @RequestMapping(value = "/event-{eventId}/participants", method = RequestMethod.GET)
    public String getParticipants(@PathVariable(value = "eventId") String eventId, Model model) {
        List<User> participantList = eventService.getParticipants( Long.parseLong(eventId));
        model.addAttribute("participantList", participantList);
        return "event/participants";
    }
}
