package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;

import java.util.List;

public interface FilterService {
    List<Event> filterOfPriority(List<Integer> validPriorities);

    List<Event> filterOfType(List<Integer> validType);
}
