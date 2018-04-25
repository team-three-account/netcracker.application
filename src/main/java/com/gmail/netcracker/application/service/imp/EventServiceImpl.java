package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class EventServiceImpl implements EventService {

    @Autowired
    Event event;

    @Autowired
    EventDao eventDao;

    @Autowired
    UserService userService;

    @Override
    public void createEventWithAuthUser(Event event) {
       // event.setCreator(userService.getAuthenticatedUser().getId());
       // eventDao.insertEvent(event);
    }
}
