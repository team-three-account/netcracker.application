package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.Filter;

import java.util.List;

public interface FilterService {

    List<Event> filtering(Filter filter, List<Event> events);

    List<Event> filterOfPriority(List<Long> validPriorities, List<Event> events);

    List<Event> filterOfType(List<Long> validType, List<Event> events);
}
