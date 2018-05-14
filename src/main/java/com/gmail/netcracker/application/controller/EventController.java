package com.gmail.netcracker.application.controller;

import com.dropbox.core.DbxException;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.validation.RegisterAndUpdateEventValidator;
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
    private ChatService chatService;

    private User authUser;
    private RegisterAndUpdateEventValidator eventValidator;

    private Logger logger = Logger.getLogger(EventController.class.getName());

    @Autowired
    public EventController(EventService eventService, NoteService noteService, PhotoServiceImp photoService,
                           UserService userService, FriendService friendService,
                           ChatService chatService, RegisterAndUpdateEventValidator eventValidator) {
        this.eventService = eventService;
        this.noteService = noteService;
        this.photoService = photoService;
        this.userService = userService;
        this.chatService = chatService;
        this.eventValidator = eventValidator;
        this.friendService = friendService;
    }


    @RequestMapping(value = "/eventlist", method = RequestMethod.GET)
    public ModelAndView eventList(ModelAndView modelAndView) {

        Long userId = userService.getAuthenticatedUser().getId();
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
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
        event.setPhoto(photoService.getDefaultImage());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/createNewEvent");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNewEvent", method = RequestMethod.POST)
    public ModelAndView saveNewEvent(@ModelAttribute("createNewEvent") Event event,
                                     BindingResult result,
                                     @RequestParam(value = "hidden") String hidden,
                                     @RequestParam(value = "photoInput") String photo,
                                     @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                     ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("event/createNewEvent");
        event.setDraft(Boolean.valueOf(hidden));
        event.setPhoto(photo);
        if ("".equals(event.getPeriodicity())) {
            event.setPeriodicity(null);
        }
        eventValidator.validate(event, result);
        if (!multipartFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !multipartFile.getContentType().equals(photoService.getImageTypeJpg())
                && !multipartFile.getContentType().equals(photoService.getImageTypePng())
                && !multipartFile.isEmpty() || result.hasErrors()) {
            modelAndView.addObject("message", "Image type don't supported");
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            event.setPhoto(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }
        photoService.saveFileInDB(event.getPhoto(), event.getEventId());
        eventService.insertEvent(event);
        if (event.getType().equals("2") || event.getType().equals("3")) {
            chatService.createChatForEvent(event, true);
            chatService.createChatForEvent(event, false);
        }

        eventService.participate(userService.getAuthenticatedUser().getId(), event.getEventId());
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
        User authUser = userService.getAuthenticatedUser();
        if (!eventService.allowAccess(authUser.getId(), eventId)) {
            modelAndView.setViewName("accessDenied");
        } else {
            modelAndView.addObject("auth_user", authUser);
            modelAndView.addObject("event", eventService.getEvent(eventId));
            modelAndView.addObject("photo", eventService.getEvent(eventId).getPhoto());
            Logger.getLogger(EventController.class.getName()).info(eventService.getEvent(eventId).getPhoto());
            modelAndView.addObject("user_creator", userService.findUserById(eventService.getEvent(eventId).getCreator()));
            int participants = eventService.countParticipants(eventId);
            modelAndView.addObject("participants", participants);
            boolean isParticipated = eventService.isParticipated(authUser.getId(), eventId);
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
    public String editPriority(@PathVariable("eventId") int eventId,
                               @ModelAttribute(value = "participation") Participant participation,
                               Model model) {

        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        eventService.setPriority(participation.getPriority(), eventId, userService.getAuthenticatedUser().getId());
        return "redirect:/account/eventList/event-" + eventId;
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
                                    @RequestParam(value = "photo") String photo,
                                    BindingResult result,
                                    ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        if ("".equals(event.getPeriodicity())) {
            event.setPeriodicity(null);
        }
        if (result.hasErrors() || !multipartFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !multipartFile.getContentType().equals(photoService.getImageTypeJpg())
                && !multipartFile.getContentType().equals(photoService.getImageTypePng())
                && !multipartFile.isEmpty()) {

            modelAndView.addObject("message", "Image type don't supported");
            modelAndView.setViewName("event/updateEvent");
            return modelAndView;
        }


        if (multipartFile.isEmpty()) {
            event.setPhoto(photo);
        } else {
            event.setPhoto(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }
        photoService.saveFileInDB(event.getPhoto(), event.getEventId());

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
    public String participate(@RequestParam(value = "event_id") String eventId, Model model) {
        model.addAttribute("auth_user", authUser);
        eventService.participate(authUser.getId(), Long.parseLong(eventId));
        return "redirect:/account/eventList/event-" + eventId;
    }

    @ModelAttribute("eventTypes")
    public List<EventType> getAllEventTypes() {
        return eventService.getAllEventTypes();
    }

    @RequestMapping(value = "/event-{eventId}/participants", method = RequestMethod.GET)
    public String getParticipants(@PathVariable(value = "eventId") String eventId, Model model) {
        List<User> participantList = eventService.getParticipants(Long.parseLong(eventId));
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
    public String unsubscribe(@RequestParam(value = "event_id") String eventId, Model model) {
        model.addAttribute("auth_user", authUser);
        eventService.unsubscribe(authUser.getId(), Long.parseLong(eventId));
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
    public String inviteListToPublic(Model model, @PathVariable(value = "eventId") int eventId) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> usersToInvite = eventService.getUsersToInvite(userService.getAuthenticatedUser().getId(), eventId);
        model.addAttribute("usersToInvite", usersToInvite);
        String message = usersToInvite.size() > 0 ? "Invite users" : "All users are subscribed on this event";
        model.addAttribute("message", message);
        model.addAttribute("eventId", eventId);
        return "/event/inviteToPublicEvent";
    }

    @RequestMapping(value = "{eventId}/invite-to-public", method = RequestMethod.POST)
    public String inviteToPublic(@PathVariable(value = "eventId") int eventId, @RequestParam(value = "userId") Long userId) {
        eventService.participate(userId, eventId);
        return "redirect:/account/public/event-" + eventId + "/invite";
    }

    @RequestMapping(value = "/for-friends/event-{eventId}/invite", method = RequestMethod.GET)
    public String inviteToForFriends(Model model, @PathVariable(value = "eventId") int eventId) {
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
                                         ModelAndView modelAndView, @PathVariable int noteId) {
        modelAndView.setViewName("event/createNewEvent");
        event.setPhoto(photoService.getDefaultImage());
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
                                        @RequestParam(value = "hidden") String hidden,
                                        @RequestParam(value = "photoInput") String photo,
                                        @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                        ModelAndView modelAndView,
                                        @PathVariable Long noteId) throws IOException, DbxException {
        modelAndView.setViewName("event/createNewEvent");
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        event.setPhoto(photo);
        eventValidator.validate(event, result);
        if (result.hasErrors() || !multipartFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !multipartFile.getContentType().equals(photoService.getImageTypeJpg())
                && !multipartFile.getContentType().equals(photoService.getImageTypePng())
                && !multipartFile.isEmpty()) {
            modelAndView.addObject("message", "Image type don't supported");
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString());
        }
        logger.info(event.getPhoto());
        photoService.saveFileInDB(event.getPhoto(), event.getEventId());
        eventService.transferNoteToEvent(noteId, userService.getAuthenticatedUser().getId(), event);
        modelAndView.setViewName("redirect:/account/managed");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/convertToEvent-{eventId}"}, method = RequestMethod.GET)
    public String convertDraftToEvent(@PathVariable int eventId) {
        eventService.convertDraftToEvent(eventId);
        return "redirect:/account/managed";
    }

    @RequestMapping(value = "/eventList/search", method = RequestMethod.POST)
    public String getSearch(Model model, String search) {
        if (search==null||search.isEmpty()) {
            return "redirect:/account/eventList";
        }
        model.addAttribute("auth_user", authUser);
        List<Event> eventListPublic = eventService.searchFromPublicEvents(search, authUser);
        model.addAttribute("resultSearchPublic", eventListPublic);
        List<Event> eventListUser = eventService.searchFromUsersEvents(search, authUser);
        model.addAttribute("resultSearchUser", eventListUser);
        return "event/resultSearch";
    }
}
