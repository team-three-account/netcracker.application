package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.EventMessage;

public interface EventMessageDao {

    void insertMessage(EventMessage message);

    EventMessage getLastMessage(Long chatId);
}
