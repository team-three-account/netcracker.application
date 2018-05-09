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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = {"/eventChat-{eventId}"}, method = RequestMethod.GET)
    public ModelAndView chatPage(@PathVariable(value = "eventId") int eventId,
                                 ModelAndView modelAndView) {
        user = userService.getAuthenticatedUser();
        List<EventMessage> list = chatService.getMessagesForEvent(eventService.getEvent(eventId));
        modelAndView.addObject("event", eventService.getEvent(eventId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("chat", list);
        logger.info(list.toString());
        modelAndView.setViewName("event/chat");
        return modelAndView;
    }
@RequestMapping(value = "/eventChat-{eventId}", method = RequestMethod.POST)
public ModelAndView sendMessage(@PathVariable(value = "eventId") String eventId,EventMessage message,ModelAndView modelAndView){
    String time = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
    message.setTime(time);
    message.setChatId(Long.valueOf(eventId));
    message.setEventId(Long.valueOf(eventId));
    message.setSenderPhoto(user.getPhoto());
    message.setSenderId(user.getId());
    logger.info(message.toString());

    try {
        eventMessageService.addNewMessage(eventService.getEvent(Math.toIntExact(message.getEventId())),
                message,
                user,
                chatService.getChat(eventService.getEvent(Math.toIntExact(message.getEventId()))));
    } catch (Throwable t) {
        logger.info(t.getMessage());
    }
    modelAndView.setViewName("event/chat");
    return modelAndView;
}

    @MessageMapping("/chat/{eventId}/{userId}")
    @SendTo("/topic/messages")
    public EventMessage send(@DestinationVariable(value = "eventId") String eventId,
                             @DestinationVariable(value = "userId")String userId,
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
                    chatService.getChat(eventService.getEvent(Math.toIntExact(message.getEventId()))));
        } catch (Throwable t) {
            logger.info(t.getMessage());
        }
        return message;


}}
