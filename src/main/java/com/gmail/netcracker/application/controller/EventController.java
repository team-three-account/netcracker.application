package com.gmail.netcracker.application.controller;

import com.dropbox.core.DbxException;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.validation.DraftValidator;
import com.gmail.netcracker.application.validation.ImageValidator;
import com.gmail.netcracker.application.validation.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/account")
public class EventController {

    private final EventService eventService;
    private final NoteService noteService;
    private final PhotoServiceImp photoService;
    private final UserService userService;
    private final FriendService friendService;
    private final DraftValidator draftValidator;
    private ChatService chatService;
    private User authUser;
    private EventValidator eventValidator;
    private final ImageValidator imageValidator;

    private Logger logger = Logger.getLogger(EventController.class.getName());

    @Autowired
    public EventController(EventService eventService, NoteService noteService, PhotoServiceImp photoService,
                           UserService userService, FriendService friendService,
                           ChatService chatService, EventValidator eventValidator,
                           DraftValidator draftValidator, ImageValidator imageValidator) {
        this.eventService = eventService;
        this.noteService = noteService;
        this.photoService = photoService;
        this.userService = userService;
        this.draftValidator = draftValidator;
        this.chatService = chatService;
        this.eventValidator = eventValidator;
        this.friendService = friendService;
        this.imageValidator = imageValidator;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.GET)
    public ModelAndView createNewEvent(@ModelAttribute(value = "createNewEvent") Event event, ModelAndView modelAndView) {
        event.setPhoto(photoService.getDefaultImageForEvents());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/createNewEvent");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@ModelAttribute("createNewEvent") Event event,
                                     BindingResult result,
                                     @RequestParam(value = "hidden") Boolean hidden,
                                     @RequestParam(value = "photoInput") String photo,
                                     @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                     ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/createNewEvent");
        event.setDraft(hidden);
        event.setPhoto(photo);
        if ("".equals(event.getPeriodicity())) {
            event.setPeriodicity(null);
        }
        if (event.getDraft().equals(true)) {
            draftValidator.validate(event, result);
            event.setType((long) 0);
        }
        if(event.getDraft().equals(false)){
            eventValidator.validate(event, result);
        }
        Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
        if (result.hasErrors() || imageFormat.equals(false)) {
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            event.setPhoto(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }

        eventService.insertEvent(event);
        if (event.getType().equals((long) 1) || event.getType().equals((long) 2) || event.getType().equals((long) 3)
                && event.getDraft().equals(false)) {
            chatService.createChatForEvent(event, true);
            chatService.createChatForEvent(event, false);
            eventService.participate(userService.getAuthenticatedUser().getId(), event.getEventId());
        }
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }


    @RequestMapping(value = {"/eventList/deleteEvent-{eventId}"}, method = RequestMethod.GET)
    public String deleteEvent(@PathVariable Long eventId) {
        if (!eventService.getEvent(eventId).getPhoto().equals(photoService.getDefaultImageForEvents())) {
            photoService.deleteFile(eventService.getEvent(eventId).getPhoto());
        }
        eventService.delete(eventId);
        return "redirect:/account/managed";
    }

    @RequestMapping(value = "/eventList/event-{eventId}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("eventId") Long eventId, ModelAndView modelAndView) {
        User authUser = userService.getAuthenticatedUser();
        if (!eventService.allowAccess(authUser.getId(), eventId)) {
            modelAndView.setViewName("accessDenied");
        } else {
            modelAndView.addObject("auth_user", authUser);
            modelAndView.addObject("event", eventService.getEvent(eventId));
            modelAndView.addObject("photo", eventService.getEvent(eventId).getPhoto());
            Logger.getLogger(EventController.class.getName()).info(eventService.getEvent(eventId).getPhoto());
            modelAndView.addObject("user_creator", userService.findUserById(eventService.getEvent(eventId).getCreator()));
            Long participants = eventService.countParticipants(eventId);
            modelAndView.addObject("participants", participants);
            Boolean isParticipated = eventService.isParticipated(authUser.getId(), eventId);
            modelAndView.addObject("participation", eventService.getParticipation(eventId));
            modelAndView.addObject("isParticipated", isParticipated);
            modelAndView.addObject("priorities", eventService.getAllPriorities());
            modelAndView.addObject("chatWithCreator", chatService.getChatByEventId(eventService.getEvent(eventId), true));
            modelAndView.addObject("chatWithOutCreator", chatService.getChatByEventId(eventService.getEvent(eventId), false));
            modelAndView.setViewName("event/viewEvent");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/event-{eventId}", method = RequestMethod.POST)
    public String editPriority(@PathVariable("eventId") Long eventId,
                               @ModelAttribute(value = "participation") Participant participation,
                               Model model) {

        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        eventService.setPriority(participation.getPriority(), eventId, userService.getAuthenticatedUser().getId());
        return "redirect:/account/eventList/event-" + eventId;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable Long eventId,
                                  ModelAndView modelAndView) {
        modelAndView.addObject("editEvent", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/updateEvent");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editevent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@ModelAttribute("editEvent") Event event,
                                    @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                    @RequestParam(value = "photo") String photo,
                                    BindingResult result,
                                    ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        event.setType(event.getTypeId());
        if ("".equals(event.getPeriodicity())) {
            event.setPeriodicity(null);
        }
        if (event.getDraft().equals(true)) {
            draftValidator.validate(event, result);
            event.setType((long) 0);
            event.setPeriodicity(null);
        } else {
            eventValidator.validate(event, result);
        }
        Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
        if (result.hasErrors() || imageFormat.equals(false)) {
            modelAndView.setViewName("event/updateEvent");
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            if (!event.getPhoto().equals(photoService.getDefaultImageForEvents())) {
                photoService.deleteFile(event.getPhoto());
            }
            event.setPhoto(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }
        modelAndView.setViewName("event/updateEvent");
        logger.info(event.toString());
        eventService.update(event);
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }

    @RequestMapping(value = "/participate", method = RequestMethod.POST)
    public String participate(@RequestParam(value = "event_id") Long eventId, Model model) {
        model.addAttribute("auth_user", authUser);
        eventService.participate(authUser.getId(), eventId);
        return "redirect:/account/eventList/event-" + eventId;
    }

    @ModelAttribute("eventTypes")
    public List<EventType> getAllEventTypes() {
        return eventService.getAllEventTypes();
    }

    @RequestMapping(value = "/event-{eventId}/participants", method = RequestMethod.GET)
    public String getParticipants(@PathVariable(value = "eventId") Long eventId, Model model) {
        List<User> participantList = eventService.getParticipants(eventId);
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("participantList", participantList);
        model.addAttribute("auth_user", authUser);
        return "event/participants";
    }

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public String available(Model model) {
        authUser = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", authUser);
        model.addAttribute("publicEventList", eventService.findPublicEvents());
        model.addAttribute("friendsEventList", eventService.findFriendsEvents(authUser.getId()));
        return "event/available";
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public String unsubscribe(@RequestParam(value = "event_id") Long eventId, Model model) {
        model.addAttribute("auth_user", authUser);
        eventService.unsubscribe(authUser.getId(), eventId);
        return "redirect:/account/eventList/event-" + eventId;
    }

    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public String getSubscriptions(Model model) {
        List<Event> eventList = eventService.getAllMyEvents();
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("eventList", eventList);
        if (eventList.isEmpty()) model.addAttribute("message", "You have not any subscription");
        else model.addAttribute("message", "You are subscribed on following events :");
        return "event/subscriptions";
    }

    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public String draft(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<Event> draftList = eventService.findDrafts(userService.getAuthenticatedUser().getId());
        model.addAttribute("draftList", draftList);
        if (draftList.isEmpty()) model.addAttribute("message", "You have not any draft");
        else model.addAttribute("message", "You're drafts :");
        return "event/draft";
    }

    @RequestMapping(value = "/managed", method = RequestMethod.GET)
    public String managed(Model model) {

        List<Event> publicEventList = eventService.findCreatedPublicEvents(userService.getAuthenticatedUser().getId());
        List<Event> privateEventList = eventService.findPrivateEvents(userService.getAuthenticatedUser().getId());
        List<Event> friendsEventList = eventService.findCreatedFriendsEvents(userService.getAuthenticatedUser().getId());
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("publicEventList", publicEventList);
        model.addAttribute("friendsEventList", friendsEventList);
        model.addAttribute("privateEventList", privateEventList);

        if (publicEventList.isEmpty() && friendsEventList.isEmpty() && privateEventList.isEmpty()) {
            model.addAttribute("message", "You have not created any event");
        } else model.addAttribute("message", "Created events :");
        return "event/managed";
    }

    @RequestMapping(value = "/public/event-{eventId}/invite", method = RequestMethod.GET)
    public String inviteListToPublic(Model model, @PathVariable(value = "eventId") Long eventId) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> usersToInvite = eventService.getUsersToInvite(userService.getAuthenticatedUser().getId(), eventId);
        model.addAttribute("usersToInvite", usersToInvite);
        String message = usersToInvite.size() > 0 ? "Invite users" : "All users are subscribed on this event";
        model.addAttribute("message", message);
        model.addAttribute("eventId", eventId);
        return "/event/inviteToPublicEvent";
    }

    @RequestMapping(value = "{eventId}/invite-to-public", method = RequestMethod.POST)
    public String inviteToPublic(@PathVariable(value = "eventId") Long eventId,
                                 @RequestParam(value = "userId") Long userId) {
        eventService.participate(userId, eventId);
        return "redirect:/account/public/event-" + eventId + "/invite";
    }

    @RequestMapping(value = "/for-friends/event-{eventId}/invite", method = RequestMethod.GET)
    public String inviteToForFriends(Model model, @PathVariable(value = "eventId") Long eventId) {
        model.addAttribute("auth_user", authUser);
        List<User> friendsToInvite = eventService.getFriendsToInvite(authUser.getId(), eventId);
        model.addAttribute("friendsToInvite", friendsToInvite);
        String message = friendsToInvite.size() > 0 ? "Invite users" : "All your friends are subscribed on this event";
        model.addAttribute("message", message);
        model.addAttribute("eventId", eventId);
        return "/event/inviteToEventForFriends";
    }

    @RequestMapping(value = {"/translateToEvent-{noteId}"}, method = RequestMethod.GET)
    public ModelAndView translateToEvent(@ModelAttribute(value = "createNewEvent") Event event,
                                         ModelAndView modelAndView,
                                         @PathVariable(value = "noteId") Long noteId) {
        modelAndView.setViewName("event/createNewEvent");
        event.setPhoto(photoService.getDefaultImageForEvents());
        logger.info(event.getPhoto());
        Note note = noteService.getNote(noteId);
        event.setName(note.getName());
        event.setDescription(note.getDescription());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        return modelAndView;
    }

    @RequestMapping(value = {"/translateToEvent-{noteId}"}, method = RequestMethod.POST)
    public ModelAndView saveNoteToEvent(@ModelAttribute("createNewEvent") Event event,
                                        BindingResult result,
                                        @RequestParam(value = "photoInput") String photo,
                                        @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                        ModelAndView modelAndView,
                                        @PathVariable Long noteId) throws IOException, DbxException {
        modelAndView.setViewName("event/createNewEvent");
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        event.setPhoto(photo);
        eventValidator.validate(event, result);
        Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
        if (result.hasErrors() || imageFormat.equals(false)) {
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString());
        }
        logger.info(event.getPhoto());
        eventService.transferNoteToEvent(noteId, userService.getAuthenticatedUser().getId(), event);
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/convertToEvent-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView getPageConvertDraftToEvent(@PathVariable Long eventId,
                                                   ModelAndView modelAndView) {
        Event event = eventService.getEvent(eventId);
        event.setPhoto(photoService.getDefaultImageForEvents());
        modelAndView.addObject("editEvent", event);

                modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/updateEvent");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/convertToEvent-{eventId}"}, method = RequestMethod.POST)
    public ModelAndView convertDraftToEvent(@PathVariable Long eventId,
                                            @ModelAttribute("editEvent") Event event,
                                            @RequestParam(value = "photo") String photo,
                                            @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                            BindingResult result,
                                            ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        event.setPhoto(photo);
        modelAndView.setViewName("event/updateEvent");
        if ("".equals(event.getPeriodicity())) {
            event.setPeriodicity(null);
        }
        eventValidator.validate(event, result);
        Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
        if (imageFormat.equals(false) || result.hasErrors()) {
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString());
        }
        eventService.update(event);
        eventService.convertDraftToEvent(eventId);
        if (event.getType().equals((long) 2) || event.getType().equals((long) 3) && event.getDraft().equals(false)) {
            chatService.createChatForEvent(event, true);
            chatService.createChatForEvent(event, false);
            eventService.participate(userService.getAuthenticatedUser().getId(), event.getEventId());
        }
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }

    @RequestMapping(value = "/{userId}/timeline", method = RequestMethod.GET)
    public String timeLine(Model model, @PathVariable Long userId) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("user_id", userId);
        return "calendar/timeline";
    }
}
