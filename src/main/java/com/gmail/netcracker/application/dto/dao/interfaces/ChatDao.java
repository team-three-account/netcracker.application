package com.gmail.netcracker.application.dto.dao.interfaces;


import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.Notification;

import java.util.List;

public interface ChatDao {

    Chat getChatByEventId(Event event, Boolean state);

    Chat getChatByChatId(Long chatId);

    void createChat(Event event, Boolean creator);

    List<EventMessage> getMessages(Long eventId, Long chatId, Boolean state);

    List<EventMessage> getMessages(Long eventId, Long chatId, Boolean state, Integer limit, Integer offset);

    void deleteChat(Event event);


    List<Notification> allUserChatId(Long userId);
    List<Notification> allUserChats(Long userId);
}
