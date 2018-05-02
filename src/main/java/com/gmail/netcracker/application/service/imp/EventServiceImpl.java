package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.dao.interfaces.EventTypeDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventType;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private EventDao eventDao;
    private EventTypeDao eventTypeDao;
    private UserService userService;

    @Autowired
    public EventServiceImpl(EventDao eventDao, EventTypeDao eventTypeDao, UserService userService) {
        this.eventDao = eventDao;
        this.eventTypeDao = eventTypeDao;
        this.userService = userService;
    }

    @Override
    public void update(Event event) {
        setPersonId(event);
        eventDao.update(event);
    }

    @Override
    public void delete(int eventId) {
        eventDao.delete(eventId);
    }

    @Override
    public void insertEvent(Event event) {
        setPersonId(event);
        eventDao.insertEvent(event);
    }

    @Override
    public Event getEvent(int eventId) {
        return eventDao.getEvent(eventId);
    }

    @Override
    public List<Event> eventList() {
        return eventDao.eventList();
    }

    @Override
    public List<Event> findPublicEvents() {
        return eventDao.findPublicEvents();
    }

    @Override
    public List<Event> findPrivateEvents(Long userId) {
        return eventDao.findPrivateEvents(userId);
    }

    @Override
    public List<Event> findFriendsEvents(Long userId) {
        return eventDao.findFriendsEvents(userId);
    }

    @Override
    public List<Event> findDrafts(Long userId) {
        return eventDao.findDrafts(userId);
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeDao.getAllEventTypes();
    }

    @Override
    public void setPersonId(Event event) {
        event.setCreator(userService.getAuthenticatedUser().getId());
    }

    @Override
    public List<Event> getAllMyEvents() {
        return eventDao.getAllMyEvents(userService.getAuthenticatedUser().getId());
    }

    @Override
    public void participate(Long user_id, long event_id) {
        eventDao.participate(user_id, event_id);
    }

    @Override
    public int countParticipants(int eventId) {
        return eventDao.getParticipantsCount(eventId);
    }

    @Override
    public List<User> getParticipants(long event_id) {
        return eventDao.getParticipants(event_id);
    }

    @Override
    public boolean isParticipated(Long id, int eventId) {
        int count = eventDao.isParticipated(id, eventId);
        return count>0 ? true : false;
    }

    @Override
    public void unsubscribe(long id, long event_id) {
        eventDao.unsubscribe(id, event_id);
    }

}
