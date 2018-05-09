package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;

public interface EventMessageDao {

    void insertMessage(Event event, EventMessage message, User user, Chat chat);


}
