package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface EventRangeService {

    List<Event> getEventsFromRange(Long userId, Long start, Long end);

    List<Event> getEventsFromRange(Long userId, Timestamp start, Timestamp end);

    List<Event> getEventsFromRange(List<Long> userList, Long start, Long end);
}
