package com.gmail.netcracker.application.dto.dao.interfaces;


import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;

import java.util.List;

public interface ChatDao {

    Chat getChatByEventId(Event event,Boolean state);

    Chat getChatByChatId(Long chatId);

    void createChat(Event event,Boolean creator);

    List<EventMessage> getMessages(Event event,Long chatId,Boolean state);

    void deleteChat(Event event);
}
