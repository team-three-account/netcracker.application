package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface EventMessageDao {

    void insertMessage(EventMessage message);

    EventMessage getLastMessage(Long chatId);
}
