package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.*;

import java.util.List;
import java.util.Map;

public interface EventService {

    void update(Event event);

    void delete(int eventId);

    void insertEvent(Event event);

    Event getEvent(int eventId);

    List<Event> eventList();

    List<Event> findPublicEvents();

    List<Event> findPrivateEvents(Long userId);

    List<Event> findFriendsEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<EventType> getAllEventTypes();

    void setPersonId(Event event);

    List<Event> getAllMyEvents();

    void participate(Long user_id, long event_id);

    int countParticipants(int eventId);

    List<User> getParticipants(long event_id);

    List<Participant> getPriorityForMyEvents();

    Map<Event, String> getMyEventWithPriority();

    List<String> getAllPriorities();

    boolean isParticipated(Long id, int eventId);

    void unsubscribe(long id, long event_id);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    int getMaxId();

    String getPriority(int event_id, Long person_id);

    String getPriority(int event_id);
}
