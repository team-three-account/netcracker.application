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
    public List<Event> filterOfPriority(List<Integer> validPriorities) {
        Map<Event, Integer> eventWithPriority = eventService.getMyEventWithPriority();
        List<Event> filterEvents = new ArrayList<>();
        for (Map.Entry<Event, Integer> entry: eventWithPriority.entrySet()){
            if(validPriorities.contains(entry.getValue())) filterEvents.add(entry.getKey());
        }
        return filterEvents;
    }

    @Override
    public List<Event> filterOfType(List<Integer> validType) {
        return null;
    }
}
