package com.gmail.netcracker.application.dto.dao.interfaces;


import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;

import java.util.List;

public interface ChatDao {

    Chat getChat(Event event);

    void createChat(Event event);

    List<EventMessage> getMessages(Event event);

    void deleteChat(Event event);
}
