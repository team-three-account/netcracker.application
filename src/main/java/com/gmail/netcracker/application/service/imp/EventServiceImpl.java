package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.dao.interfaces.EventTypeDao;
import com.gmail.netcracker.application.dto.dao.interfaces.ParticipantDao;
import com.gmail.netcracker.application.dto.dao.interfaces.PriorityDao;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {
    private EventDao eventDao;
    @Autowired
    private ParticipantDao participantDao;
    private EventTypeDao eventTypeDao;
    private UserService userService;
    @Autowired
    private PriorityDao priorityDao;

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
    public void participate(Long userId, long eventId) {
        eventDao.participate(userId, eventId);
    }

    @Override
    public int countParticipants(int eventId) {
        return eventDao.getParticipantsCount(eventId);
    }

    @Override
    public List<User> getParticipants(long eventId) {
        return eventDao.getParticipants(eventId);
    }

    @Override
    public boolean isParticipated(Long id, int eventId) {
        return eventDao.isParticipated(id, eventId) != null ;
    }

    @Override
    public void unsubscribe(long id, long eventId) {
        eventDao.unsubscribe(id, eventId);
    }

    @Override
    public int getMaxId() {
        return eventDao.getMaxId();
    }

    @Override
    public Integer getPriority(int eventId, Long personId) {
        return participantDao.getParticipant(eventId, personId).getPriority();
    }

    @Override
    public Integer getPriority(int eventId) {
        return getPriority(eventId, userService.getAuthenticatedUser().getId());
    }

    @Override
    public boolean allowAccess(Long personId, int eventId) {
        boolean access = false;
        switch( eventDao.getEventType(eventId) ) {
            case 1: // private
                access = isCreator(personId, eventId);
                break;
            case 2 : // public
                access = true;
                break;
            case 3:
                access = true;// for friends
                break;
        }
       return access;
    }

    public boolean isCreator(Long personId, int eventId){
        return eventDao.isCreator(personId, eventId) >0 ;
    }

    @Override
    public void setPriority(Integer priority, int eventId, Long user_id) {
        participantDao.setPriority(priority, eventId, user_id);
    }

    @Override
    public Participant getParticipant(int eventId, Long personId) {
        return participantDao.getParticipant(eventId, personId);
    }

    @Override
    public Participant getParticipant(int eventId) {
        return getParticipant(eventId, userService.getAuthenticatedUser().getId());
    }

    @Override
    public List<Participant> getPriorityForMyEvents() {
        return participantDao.getPriorityForUserEvents(userService.getAuthenticatedUser().getId());
    }

    @Override
    public Map<Event, Integer> getMyEventWithPriority(){
        Map<Event, Integer> eventWithPriority = new HashMap<>();
        for(Participant participant: getPriorityForMyEvents()){
            eventWithPriority.put(getEvent(participant.getEventId()), participant.getPriority());
        }
        return eventWithPriority;
    }

    @Override
    public List<Priority> getAllPriorities() {
        return priorityDao.getAllPriority();
    }

    @Override
    public List<Event> findCreatedFriendsEvents(Long id) {
        return eventDao.findCreatedFriendsEvents(id);
    }

    @Override
    public List<Event> findCreatedPublicEvents(Long id) {
        return eventDao.findCreatedPublicEvents(id);
    }

    @Override
    public List<User> subtraction(List<User> minuend, List<User> subtrahend) {
        for (User item : subtrahend) {
            if (minuend.contains(item)) {
                minuend.remove(item);
            }
        }
        return minuend;
    }

    @Override
    public List<User> getUsersToInvite(Long currentId, int eventId) {
        List<User> minuend = userService.getAllUsers(currentId);
        List<User> subtrahend = getParticipants(eventId);
        return subtraction(minuend, subtrahend);
    }

}
