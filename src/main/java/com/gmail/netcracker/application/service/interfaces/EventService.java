package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.EventType;

import java.util.List;

public interface EventService {

    void update(Event event);

    void delete(int eventId);

    void insertEvent(Event event);

    Event getEvent(int eventId);

    List<Event> eventList();

    List<EventType> getAllEventTypes();

    void setPersonId(Event event);

    List<Event> getAllMyEvents();

    void participate(Long user_id, long event_id);
}
