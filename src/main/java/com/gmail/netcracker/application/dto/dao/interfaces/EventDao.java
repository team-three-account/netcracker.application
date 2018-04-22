package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Event;

import java.util.List;

public interface EventDao {
    void update(Event event);

    void delete(int eventId);

    void insertEvent(Event event);

    List<Event> eventList();

    List<Event> findAllEventTypes();
}
