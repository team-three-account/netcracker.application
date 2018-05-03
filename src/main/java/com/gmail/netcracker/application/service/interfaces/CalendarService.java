package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Priority;
import java.util.List;

public interface CalendarService {

    List<Event> filterOfPriority(List<Integer> validPriorities);
}
