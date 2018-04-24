package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;

import java.util.List;

public interface EventService {
    void createEventWithAuthUser(Event Event);

    public List<Event> findAll();
}
