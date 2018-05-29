package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventMessageDao;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.service.interfaces.EventMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventMessageServiceImpl implements EventMessageService {

    @Autowired
    private EventMessageDao eventMessageDao;

    @Override
    @Transactional
    public void addNewMessage(EventMessage message) {
        eventMessageDao.insertMessage(message);
    }

    @Override
    @Transactional(readOnly = true)
    public EventMessage getLastMessage(Long chatId) {
        return eventMessageDao.getLastMessage(chatId);
    }
}
