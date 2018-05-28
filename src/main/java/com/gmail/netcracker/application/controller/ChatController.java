package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.interfaces.ChatService;
import com.gmail.netcracker.application.service.interfaces.EventMessageService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class controller for chat pages.
 */

@Controller
@RequestMapping(value = "/account/eventList")
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private EventMessageService eventMessageService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private User user;

    @Autowired
    private Event event;

    private Logger logger = Logger.getLogger(ChatService.class.getName());


    /**
     * This method returns  page for all chats for current user.
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */

    @RequestMapping(value = {"/chats/{userId}"}, method = RequestMethod.GET)
    public ModelAndView mainChatPage(@PathVariable(value = "userId") Long userId,
                                     ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("allChats", chatService.allUserChats(userId));
        modelAndView.setViewName("account/chatlist");
        return modelAndView;
    }

    /**
     * This method returns  page for chat with creator in such event.
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */

    @RequestMapping(value = {"/eventChat/main/{chatId}-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView mainChatPage(@PathVariable(value = "eventId") Long eventId,
                                     @PathVariable(value = "chatId") Long chatId,
                                     ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        List<EventMessage> list = chatService.getMessagesForEvent(eventId, chatId, true);
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("participants", eventService.getParticipants(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("chat", chatService.getChatByEventId(eventService.getEvent(eventId), true));
        logger.info(list.toString());
        modelAndView.setViewName("event/chat");
        return modelAndView;
    }

    /**
     * This REST method returns JSON for all chat id for user .
     *
     * @param Long modelAndView Object class {@link Long}
     * @return List
     */

    @RequestMapping(value = "/eventChat/main/notification", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Notification> getNotificationForMessages(@RequestParam Long authUserId) {
        logger.info(chatService.allUserChatId(authUserId).toString());
        return chatService.allUserChatId(authUserId);
    }

    /**
     * This REST method returns next 15 messages in chat .
     *
     * @param Long modelAndView Object class {@link Long}
     * @return List
     */

    @RequestMapping(value = "/eventChat/main/getChatMessages", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<EventMessage> getMessagesForEvent(@RequestParam Long eventId,
                                                  @RequestParam Long chatId,
                                                  @RequestParam Boolean state,
                                                  @RequestParam Integer limit,
                                                  @RequestParam Integer offset) {
        return chatService.getMessagesForEvent(eventId, chatId, state, limit, offset);
    }

    /**
     * This method returns  page for chat without creator in such event.
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */

    @RequestMapping(value = {"/eventChat/subscriptions/{chatId}-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView subscriptionsChatPage(@PathVariable(value = "eventId") Long eventId,
                                              @PathVariable(value = "chatId") Long chatId,
                                              ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        List<EventMessage> list = chatService.getMessagesForEvent(eventId, chatId, false);
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("participants", eventService.getParticipants(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("chat", chatService.getChatByEventId(eventService.getEvent(eventId), false));
        modelAndView.setViewName("event/chat");
        return modelAndView;
    }

    /**
     * This method sed new message in chat.
     *
     * @param eventMessage modelAndView Object class {@link EventMessage}
     * @param Long  Object class {@link Long}
     * @param Long Object class {@link Long}
     * @param Long modelAndView Object class {@link Long}
     * @return EventMessage
     */

    @MessageMapping("/chat/{eventId}/{userId}/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public EventMessage send(@DestinationVariable(value = "eventId") String eventId,
                             @DestinationVariable(value = "userId") String userId,
                             @DestinationVariable(value = "chatId") String chatId,
                             EventMessage message) throws Exception {
        user = userService.findUserById(Long.valueOf(userId));
        event = eventService.getEvent(Long.valueOf(eventId));
        message.setTime(Utilities.getCurrentDateInString());
        message.setChatId(Long.valueOf(chatId));
        message.setEventId(Long.valueOf(eventId));
        message.setSenderId(user.getId());
        logger.info(message.toString());
        try {
            eventMessageService.addNewMessage(message);
        } catch (Throwable t) {
            logger.info(t.getMessage());
        }
        return message;
    }


}
