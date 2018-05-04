package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.*;

import java.util.List;
import java.util.Map;

public interface EventService {

    void update(Event event);

    void delete(int eventId);

    void insertEvent(Event event);

    Event getEvent(int eventId);

    List<Event> eventList();

    List<Event> findPublicEvents();

    List<Event> findPrivateEvents(Long userId);

    List<Event> findFriendsEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<EventType> getAllEventTypes();

    void setPersonId(Event event);

    List<Event> getAllMyEvents();

    void participate(Long userId, long eventId);

    int countParticipants(int eventId);

    List<User> getParticipants(long eventId);

    List<Participant> getPriorityForMyEvents();

    Map<Event, Integer> getMyEventWithPriority();

    List<Priority> getAllPriorities();

    boolean isParticipated(Long id, int eventId);

    void unsubscribe(long id, long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    int getMaxId();

    Integer getPriority(int eventId, Long personId);

    Integer getPriority(int eventId);

    void setPriority(Integer priority, int eventId, Long userId);

    Participant getParticipant(int eventId, Long personId);

    Participant getParticipant(int eventId);


    boolean allowAccess(Long personId, int eventId);

    boolean isCreator(Long personId, int eventId);

    List<User> getUsersToInvite(Long id, int eventId);

    List<User> subtraction(List<User> minuend, List<User> subtrahend);
}
