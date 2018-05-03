package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Priority;
import java.util.List;

public interface CalendarService {

    public List<Event> filterOfPriority(List<String> validPriorities);
}
