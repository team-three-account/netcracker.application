package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ChatDao;
import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.service.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

    private Logger logger = Logger.getLogger(ChatServiceImpl.class.getName());

    @Override
    public Chat getChat(Event event) {
        return chatDao.getChat(event);
    }

    @Override
    public void createChatForEvent(Event event) {
        chatDao.createChat(event);
    }

    @Override
    public List<EventMessage> getMessagesForEvent(Event event) {
        return chatDao.getMessages(event);
    }

    @Override
    public void deleteEventChat(Event event) {
        chatDao.deleteChat(event);
    }
}
