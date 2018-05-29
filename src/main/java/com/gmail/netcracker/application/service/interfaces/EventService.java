package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventService {

    void update(Event event);

    void delete(Long eventId);

    void insertEvent(Event event);

    Event getEvent(Long eventId);

    List<Event> findPrivateEvents(Long userId);

    List<Event> findAvailableEvents();

    List<Event> findDrafts(Long userId);

    List<EventType> getAllEventTypes();

    void setEventCreator(Event event);

    List<Event> findEventSubscriptions();

    void participate(Long userId, Long eventId);

    Long countParticipants(Long eventId);

    List<User> getParticipants(Long eventId);

    List<Priority> getAllPriorities();

    Boolean isParticipantOfEvent(Long id, Long eventId);

    void unsubscribe(Long id, Long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);


    void setPriority(Long priority, Long eventId, Long userId);

    Boolean allowAccess(Long personId, Long eventId);

    Boolean isCreator(Long personId, Long eventId);

    List<User> findUserForInvite(Long id, Long eventId);

    List<User> findFriendsForInvite(Long id, Long eventId);

    void convertNoteToEvent(Long noteId, Long userId, Event event);

    List<Event> myEventsWithPriority();

    Event findEventSubscriptionsWithPriority(Long eventId);

    Participant getParticipation(Long eventId);

    void convertDraftToEvent(Event event);

    List<Event> getTimelines(Long id);

    Event copyEvent(Event toCopy);

    Long getDurationFromStartAndEnd(String start, String end);

    String getEndDateFromDuration(String start, Long duration);

    String getEndDateFromDuration(Timestamp start, Long duration);

    void updateNotificationSchedule(User user);

    void disableNotifications(Long userId);
}
