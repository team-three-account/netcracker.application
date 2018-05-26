package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventMessageDao;
import com.gmail.netcracker.application.dto.model.Chat;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventMessageService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventMessageServiceImpl implements EventMessageService {

    @Autowired
    private EventMessageDao eventMessageDao;

    @Override
    public void addNewMessage( EventMessage message) {
        eventMessageDao.insertMessage( message);
    }
}
