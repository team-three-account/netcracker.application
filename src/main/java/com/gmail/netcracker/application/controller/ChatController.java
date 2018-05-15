package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.ChatServiceImpl;
import com.gmail.netcracker.application.service.imp.EventMessageServiceImpl;
import com.gmail.netcracker.application.service.imp.EventServiceImpl;
import com.gmail.netcracker.application.service.imp.UserServiceImp;
import com.gmail.netcracker.application.service.interfaces.ChatService;
import com.gmail.netcracker.application.service.interfaces.EventMessageService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

    private User user;


    private Logger logger = Logger.getLogger(ChatService.class.getName());

    @RequestMapping(value = {"/eventChat/main/{chatId}-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView mainChatPage(@PathVariable(value = "eventId") int eventId,
                                     @PathVariable(value = "chatId") Long chatId,
                                     ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        List<EventMessage> list = chatService.getMessagesForEvent((long) eventId, chatId, true);
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
//        modelAndView.addObject("chatMessage", chatService.getMessagesForEvent((long) eventId, chatId, true));
        modelAndView.addObject("chat", chatService.getChatByEventId(eventService.getEvent(eventId), true));
        logger.info(list.toString());
        modelAndView.setViewName("event/chat");
        return modelAndView;
    }

    @RequestMapping(value = "/eventChat/main/getChatMessages", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<EventMessage> getMessagesForEvent(@RequestParam Long eventId,
                                                  @RequestParam Long chatId,
                                                  @RequestParam Boolean state,
                                                  @RequestParam Integer limit,
                                                  @RequestParam Integer offset) {
        return chatService.getMessagesForEvent(eventId, chatId, state, limit, offset);
    }

    @RequestMapping(value = {"/eventChat/subscriptions/{chatId}-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView subscriptionsChatPage(@PathVariable(value = "eventId") int eventId,
                                              @PathVariable(value = "chatId") Long chatId,
                                              ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        List<EventMessage> list = chatService.getMessagesForEvent((long) eventId, chatId, false);
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
//        modelAndView.addObject("chatMessage", chatService.getMessagesForEvent((long) eventId, chatId, false));
        modelAndView.addObject("chat", chatService.getChatByEventId(eventService.getEvent(eventId), false));
        logger.info(list.toString());
        modelAndView.setViewName("event/chat");
        return modelAndView;
    }


    @MessageMapping("/chat/{eventId}/{userId}/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public EventMessage send(@DestinationVariable(value = "eventId") String eventId,
                             @DestinationVariable(value = "userId") String userId,
                             @DestinationVariable(value = "chatId") String chatId,
                             EventMessage message) throws Exception {
        User user = userService.findUserById(Long.valueOf(userId));


        String time = Utilites.timeStamp();
        message.setTime(time);
        message.setChatId(Long.valueOf(eventId));
        message.setEventId(Long.valueOf(eventId));
        logger.info(message.toString());


        try {
            eventMessageService.addNewMessage(eventService.getEvent(Math.toIntExact(message.getEventId())),
                    message,
                    user,
                    chatService.getChatByChatId(Long.valueOf(chatId)));
        } catch (Throwable t) {
            logger.info(t.getMessage());
        }
        return message;


    }
}
