package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;

import java.util.List;


public interface ChatService {


    Chat getChat(Event event);

    void createChatForEvent(Event event);

    List<EventMessage> getMessagesForEvent(Event event);

    void deleteEventChat(Event event);
}
