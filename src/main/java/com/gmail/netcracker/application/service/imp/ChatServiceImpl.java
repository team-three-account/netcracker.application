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
    public Chat getChatByEventId(Event event, Boolean state) {
        return chatDao.getChatByEventId(event, state);
    }

    @Override
    public Chat getChatByChatId(Long chatId) {
        return chatDao.getChatByChatId(chatId);
    }

    @Override
    public void createChatForEvent(Event event, Boolean creator) {
        chatDao.createChat(event, creator);
    }

    @Override
    public List<EventMessage> getMessagesForEvent(Event event, Long chatId, Boolean state) {
        return chatDao.getMessages(event, chatId, state);
    }

    @Override
    public void deleteEventChat(Event event) {
        chatDao.deleteChat(event);
    }
}
