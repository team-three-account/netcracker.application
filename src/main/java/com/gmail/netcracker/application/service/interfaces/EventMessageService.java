package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;

public interface EventMessageService {

    void addNewMessage(EventMessage message);

    EventMessage getLastMessage(Long chatId);
}
