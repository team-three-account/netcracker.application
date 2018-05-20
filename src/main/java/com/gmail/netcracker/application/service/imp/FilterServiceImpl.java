package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private EventService eventService;

    @Override
    public List<Event> filterOfPriority(List<Long> validPriorities) {
        if (validPriorities.isEmpty()) return eventService.myEventsWithPriority();
        List<Event> filterEvents = new ArrayList<>();
        for (Event event: eventService.myEventsWithPriority()){
            if(validPriorities.contains(event.getPriorityId()))
                filterEvents.add(event);
        }
        return filterEvents;
    }

    @Override
    public List<Event> filterOfType(List<Long> validType) {
        if (validType.isEmpty()) return eventService.getAllMyEvents();
        List<Event> filterEvents = new ArrayList<>();
        for (Event event: eventService.getAllMyEvents()){
            if(validType.contains(event.getTypeId()))
                filterEvents.add(event);
        }
        return filterEvents;
    }
}
