package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface CalendarService {
    List<Event> getEventsFromRange(User user, Long start, Long end);
}
