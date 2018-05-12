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

    List<Priority> getAllPriorities();

    boolean isParticipated(Long id, int eventId);

    void unsubscribe(long id, long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    int getMaxId();

    void setPriority(Integer priority, int eventId, Long userId);

    boolean allowAccess(Long personId, int eventId);

    boolean isCreator(Long personId, int eventId);

    List<User> getUsersToInvite(Long id, int eventId);

    List<User> subtraction(List<User> minuend, List<User> subtrahend);

    List<User> getFriendsToInvite(Long id, int eventId);

    void transferNoteToEvent(Long noteId,Long userId, Event event);

    List<Event> myEventsWithPriority();

    Event getMyEventWithPriority(int eventId);

    Participant getParticipation(int eventId);

    void convertDraftToEvent(int eventId);
}
