package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventType;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.PhotoService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.RegisterAndUpdateEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/account")
public class EventController {

    private final EventService eventService;
    private final NoteService noteService;

    private final PhotoService photoService;
    private final UserService userService;

    private final RegisterAndUpdateEventValidator eventValidator;

    @Autowired
    public EventController(EventService eventService, NoteService noteService, PhotoService photoService, UserService userService,
                           RegisterAndUpdateEventValidator eventValidator) {
        this.eventService = eventService;
        this.noteService = noteService;
        this.photoService = photoService;
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
        event.setPhoto("1");
        modelAndView.setViewName("event/createNewEvent");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@ModelAttribute("createNewEvent") Event event,
                                     BindingResult result,
                                     @RequestParam(value = "hidden") String hidden,
                                     @RequestParam(value = "photoInput") String photo,
                                     @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                     ModelAndView modelAndView) {
        modelAndView.setViewName("event/createNewEvent");
        event.setDraft(Boolean.valueOf(hidden));
        event.setPhoto(photo);
        eventValidator.validate(event, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (!photo.equals(1)) {
            photoService.saveFileInFileSystem(multipartFile,String.valueOf(System.currentTimeMillis()));
        }
        photoService.saveFileInDB(event.getPhoto(), Long.parseLong(String.valueOf(event.getEventId())));
        eventService.insertEvent(event);
        eventService.participate(userService.getAuthenticatedUser().getId(),Long.parseLong(String.valueOf(eventService.getMaxId())));
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }


    @RequestMapping(value = {"/eventList/deleteEvent-{eventId}"}, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable int eventId) {
        eventService.delete(eventId);
        return "redirect:/account/managed";
    }

    @RequestMapping(value = "/eventList/event-{eventId}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("eventId") int eventId, ModelAndView modelAndView) {
        User auth_user = userService.getAuthenticatedUser();
        modelAndView.addObject("auth_user", auth_user);
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("photo", eventService.getEvent(eventId).getPhoto());
        Logger.getLogger(EventController.class.getName()).info(eventService.getEvent(eventId).getPhoto());
        modelAndView.addObject("user_creator", userService.findUserById(eventService.getEvent(eventId).getCreator()));
        int participants = eventService.countParticipants(eventId);
        modelAndView.addObject("participants", participants );
        boolean isParticipated = eventService.isParticipated(auth_user.getId(), eventId);
        modelAndView.addObject("isParticipated", isParticipated );
        modelAndView.setViewName("event/viewEvent");
        return modelAndView;
    }


    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable int eventId,
                                  ModelAndView modelAndView) {
        modelAndView.addObject("editEvent", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/updateEvent");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@ModelAttribute("editEvent") Event event,
                                    @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                    BindingResult result,
                                    ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        event.setPhoto(String.valueOf(System.currentTimeMillis()));
        photoService.saveFileInFileSystem(multipartFile,event.getPhoto());
        photoService.saveFileInDB(event.getPhoto(),Long.parseLong(String.valueOf(event.getEventId())));
        modelAndView.setViewName("event/updateEvent");
        eventValidator.validate(event, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        eventService.update(event);
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }

    @RequestMapping(value = "/participate", method = RequestMethod.POST)
    public String deleteFriend(@RequestParam(value = "event_id") String event_id, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        eventService.participate(userService.getAuthenticatedUser().getId(), Long.parseLong(event_id));
        return "redirect:/account/eventList/event-"+ event_id;
    }

    @ModelAttribute("eventTypes")
    public List<EventType> getAllEventTypes() {
        return eventService.getAllEventTypes();
    }

    @RequestMapping(value = "/event-{eventId}/participants", method = RequestMethod.GET)
    public String getParticipants(@PathVariable(value = "eventId") String eventId, Model model) {
        List<User> participantList = eventService.getParticipants( Long.parseLong(eventId));
        model.addAttribute("participantList", participantList);
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "event/participants";
    }

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public String available( Model model) {
        User auth_user = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", auth_user);
        model.addAttribute("publicEventList", eventService.findPublicEvents());
        model.addAttribute("friendsEventList", eventService.findFriendsEvents(auth_user.getId()));
        return "event/available";
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(@RequestParam(value = "event_id") String event_id, Model model) {
        User auth_user =userService.getAuthenticatedUser();
        model.addAttribute("auth_user", auth_user);
        eventService.unsubscribe(auth_user.getId(), Long.parseLong(event_id));
        return "redirect:/account/eventList/event-"+ event_id;
    }

    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public String getSubscriptions(Model model) {
        List<Event> eventList = eventService.getAllMyEvents();
        model.addAttribute("eventList", eventList);
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        if (eventList.isEmpty()) model.addAttribute("message", "You have not any subscription");
        else model.addAttribute("message", "You are subscribed on following events :");
        return "event/subscriptions";
    }

    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public String draft(Model model) {
        User auth_user =userService.getAuthenticatedUser();
        model.addAttribute("auth_user", auth_user);
        List<Event> draftList = eventService.findDrafts(auth_user.getId());
        model.addAttribute("draftList", draftList);
        if (draftList.isEmpty()) model.addAttribute("message", "You have not any draft");
        else model.addAttribute("message", "You're drafts :");
        return "event/draft";
    }

    @RequestMapping(value = "/managed", method = RequestMethod.GET)
    public String managed(Model model) {
        User auth_user =userService.getAuthenticatedUser();
        model.addAttribute("auth_user", auth_user);
        List<Event> publicEventList = eventService.findCreatedPublicEvents(auth_user.getId()); //!!!!
        List<Event> privateEventList = eventService.findPrivateEvents(auth_user.getId());
        List<Event> friendsEventList = eventService.findCreatedFriendsEvents(auth_user.getId()); //!!!
        model.addAttribute("publicEventList", publicEventList);
        model.addAttribute("friendsEventList", friendsEventList);
        model.addAttribute("privateEventList", privateEventList);

        if (publicEventList.isEmpty() && friendsEventList.isEmpty() && privateEventList.isEmpty()){
            model.addAttribute("message", "You have not created any event");
        }
        else model.addAttribute("message", "Created events :");
        return "event/managed";
    }
}
