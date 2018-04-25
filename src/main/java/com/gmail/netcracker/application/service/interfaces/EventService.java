package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;

import java.util.List;

public interface EventService {
    // crud
    void update(Event event);

    void delete(int eventId);

    void insertEvent(Event event);

    Event getEvent(int eventId);

    List<Event> eventList();

    List<Event> findAllEventTypes();

    void setPersonIdAndDraftStatus(Event event);
}
