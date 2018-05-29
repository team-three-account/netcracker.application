package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FilterService;
import com.gmail.netcracker.application.utilites.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class FilterServiceImpl implements FilterService {


    @Override
    public List<Event> filtering(Filter filter, List<Event> events) {
        return filterOfPriority(filter.getPriorities(),
                filterOfType(filter.getEventTypes(), events));
    }

    @Override
    public List<Event> filterOfPriority(List<Long> validPriorities, List<Event> events) {
        if (validPriorities.isEmpty()) return events;
        List<Event> filterEvents = new ArrayList<>();
        for (Event event: events){
            if(validPriorities.contains(event.getPriorityId()))
                filterEvents.add(event);
        }
        return filterEvents;
    }

    @Override
    public List<Event> filterOfType(List<Long> validType, List<Event> events) {
        if (validType.isEmpty()) return events;
        List<Event> filterEvents = new ArrayList<>();
        for (Event event: events){
            if(validType.contains(event.getTypeId()))
                filterEvents.add(event);
        }
        return filterEvents;
    }
}
