package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.Notification;

import java.util.List;


public interface ChatService {


    Chat getChatByEventId(Event event, Boolean state);

    Chat getChatByChatId(Long chatId);

    void createChatForEvent(Event event, Boolean creator);

    List<EventMessage> getMessagesForEvent(Long eventId, Long chatId, Boolean state);

    List<EventMessage> getMessagesForEvent(Long eventId, Long chatId, Boolean state, Integer limit, Integer offset);

    void deleteEventChat(Event event);

    List<Notification> allUserChatId(Long userId);

    List<Notification> allUserChats(Long userId);
}
