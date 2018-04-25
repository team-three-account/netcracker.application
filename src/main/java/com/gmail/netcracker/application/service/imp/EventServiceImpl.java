package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EventServiceImpl implements EventService {

    @Autowired
    Event event;

    @Autowired
    EventDao eventDao;

    @Autowired
    UserService userService;

    //crud
    @Override
    public void update(Event event) {
        setPersonIdAndDraftStatus(event);
        eventDao.update(event);
    }

    @Override
    public void delete(int eventId) {
        eventDao.delete(eventId);
    }

    @Override
    public void insertEvent(Event event) {
        setPersonIdAndDraftStatus(event);
        eventDao.insertEvent(event);
    }

    @Override
    public Event getEvent(int eventId) {
        Event event = eventDao.getEvent(eventId);
        return event;
    }

    @Override
    public List<Event> eventList() {
        List<Event> eventList = eventDao.eventList();
        return eventList;
    }

    @Override
    public List<Event> findAllEventTypes() {
        List<Event> eventList = eventDao.findAllEventTypes();
        return eventList;
    }

    @Override
    public void setPersonIdAndDraftStatus(Event event) {
        event.setCreator(userService.getAuthenticatedUser().getId());
        if (event.getDateStart().equals("") || event.getDateEnd().equals("")) {
            event.setDraft(true);
        }
    }

    public List<Event> findAll(){
        return eventDao.eventList();
    }
}
