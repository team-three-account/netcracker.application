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

    List<Event> eventList();

    List<Event> findPublicEvents();

    List<Event> findPrivateEvents(Long userId);

    List<Event> findFriendsEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<EventType> getAllEventTypes();

    void setPersonId(Event event);

    List<Event> getAllMyEvents();

    void participate(Long userId, Long eventId);

    Long countParticipants(Long eventId);

    List<User> getParticipants(Long eventId);

    List<Priority> getAllPriorities();

    Boolean isParticipated(Long id, Long eventId);

    void unsubscribe(Long id, Long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);


    void setPriority(Long priority, Long eventId, Long userId);

    Boolean allowAccess(Long personId, Long eventId);

    Boolean isCreator(Long personId, Long eventId);

    List<User> getUsersToInvite(Long id, Long eventId);

    List<User> getFriendsToInvite(Long id, Long eventId);

    void transferNoteToEvent(Long noteId,Long userId, Event event);

    List<Event> myEventsWithPriority();

    Event getMyEventWithPriority(Long eventId);

    Participant getParticipation(Long eventId);

    void convertDraftToEvent(Long eventId);

    List<Event> getTimelines(Long id);

    Event copyEvent(Event toCopy);

    Long getDurationFromStartAndEnd(String start, String end);

    String getEndDateFromDuration(String start, Long duration);

    String getEndDateFromDuration(Timestamp start, Long duration);

    List<Event> searchByUserFromRange(Long userId, Timestamp start, Timestamp end);

    List<Event> searchByUserFromRange(Long userId, Long start, Long end);

    List<Event> getEventsFromRange(Timestamp fromDate, Timestamp tillDate, Long id);

    void updateNotificationSchedule(User user);

    void disableNotifications(Long userId);
}
