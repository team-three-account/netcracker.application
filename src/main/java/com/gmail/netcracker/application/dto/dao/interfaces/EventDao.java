package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.User;

import java.sql.Timestamp;
import java.util.List;

public interface EventDao {
    void update(Event event);

    void delete(Long eventId);

    void insertEvent(Event event);

    Event getEvent(Long eventId);

    List<Event> findPrivateEvents(Long userId);

    List<Event> findAvailableEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<Event> findEventSubscriptions(Long personId);

    void participate(Long userId, Long eventId);

    Long getParticipantsCount(Long eventId);

    List<User> getParticipants(Long eventId);

    Participant isParticipantOfEvent(Long id, Long eventId);

    void unsubscribe(Long userId, Long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    Long getEventType(Long eventId);

    User getCreator(Long eventId);

    Event checkCreatorById(Long personId, Long eventId);

    Event findEventSubscriptionsWithPriority(Long personId, Long eventId);

    List<Event> listEventsWithPriority(Long personId);

    void convertDraftToEvent(Event event);

    List<Event> searchInPublic(String query, Long userId);

    List<Event> searchInUsersEvents(String query, Long userId);

    List<Event> getAllPersonEvents(Long id);

    List<Event> searchByUserFromRange(Long userId, Timestamp start, Timestamp end);

    List<Event> getEventsFromRange(Timestamp fromDate, Timestamp tillDate, Long id);

    List<User> findUsersForInvite(Long currentId, Long eventId);

    List<User> findFriendsForInvite(Long id, Long eventId);
}
