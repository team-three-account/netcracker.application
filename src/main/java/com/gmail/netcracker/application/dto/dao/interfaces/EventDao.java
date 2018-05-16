package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface EventDao {
    void update(Event event);

    void delete(Long eventId);

    void insertEvent(Event event);

    Event getEvent(Long eventId);

    List<Event> eventList();

    List<Event> findPublicEvents();

    List<Event> findPrivateEvents(Long userId);

    List<Event> findFriendsEvents(Long userId);

    List<Event> findDrafts(Long userId);

    List<Event> getAllMyEvents(Long personId);

    void participate(Long userId, Long eventId);

    int getParticipantsCount(Long eventId);

    List<User> getParticipants(Long eventId);

    Participant isParticipated(Long id, Long eventId);

    void unsubscribe(Long id, Long eventId);

    List<Event> findCreatedFriendsEvents(Long id);

    List<Event> findCreatedPublicEvents(Long id);

    int getMaxId();

    int getEventType(Long eventId);

    User getCreator(Long eventId);

    Event checkCreatorById(Long personId, Long eventId);

    Event getEventWithPriority(Long personId, Long eventId);

    List<Event> listEventsWithPriority(Long personId);

    void convertDraftToEvent(Event event);

    List<Event> searchInPublic(String query, Long userId);

    List<Event> searchInUsersEvents(String query, Long userId);
}
