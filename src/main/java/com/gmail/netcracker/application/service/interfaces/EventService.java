package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventType;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

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

    void participate(Long user_id, long event_id);

    int countParticipants(int eventId);

    List<User> getParticipants(long event_id);
}
