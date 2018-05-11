package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;

import java.util.List;


public interface ChatService {


    Chat getChatByEventId(Event event, Boolean state);

    Chat getChatByChatId(Long chatId);

    void createChatForEvent(Event event, Boolean creator);

    List<EventMessage> getMessagesForEvent(Event event, Long chatId, Boolean state);

    void deleteEventChat(Event event);
}
