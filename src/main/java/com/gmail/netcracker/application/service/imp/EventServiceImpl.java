package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.*;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.Utilities;
import com.gmail.netcracker.application.utilites.scheduling.JobSchedulingManager;
import com.gmail.netcracker.application.utilites.scheduling.jobs.EventNotificationJob;
import com.gmail.netcracker.application.utilites.scheduling.jobs.PersonalPlanNotificationJob;
import org.bouncycastle.asn1.cms.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;

/**
 * This class is a event service which connects DAO layer and controller.
 */

@Service
public class EventServiceImpl implements EventService {
    private EventDao eventDao;
    private EventTypeDao eventTypeDao;
    private UserService userService;
    private FriendService friendService;
    private PriorityDao priorityDao;
    private NoteDao noteDao;
    private ItemDao itemDao;
    private UserDao userDao;

    private JobSchedulingManager jobSchedulingManager;
    private EmailConstructor emailConstructor;

    @Autowired
    public EventServiceImpl(EventDao eventDao, EventTypeDao eventTypeDao, UserService userService,
                            FriendService friendService, PriorityDao priorityDao, NoteDao noteDao, ItemDao itemDao,
                            JobSchedulingManager jobSchedulingManager, EmailConstructor emailConstructor, UserDao userDao) {
        this.eventDao = eventDao;
        this.eventTypeDao = eventTypeDao;
        this.userService = userService;
        this.friendService = friendService;
        this.priorityDao = priorityDao;
        this.noteDao = noteDao;
        this.itemDao = itemDao;
        this.jobSchedulingManager = jobSchedulingManager;
        this.emailConstructor = emailConstructor;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void update(Event event) {
        setPersonId(event);
        event.setDuration(getDurationFromStartAndEnd(event.getDateStart(), event.getDateEnd()));
        eventDao.update(event);
        deleteEventNotificationJob(event.getEventId());
        if (event.getPeriodicity() != null) scheduleEventNotificationJob(event);
    }

    @Override
    @Transactional
    public void delete(Long eventId) {
        itemDao.cancelItemsBookingFromEvent(eventId);
        eventDao.delete(eventId);
        deleteEventNotificationJob(eventId); //TODO check next case: will be this method executed if transaction is failed
    }

    @Override
    @Transactional
    public void insertEvent(Event event) {
        setPersonId(event);
        event.setDuration(getDurationFromStartAndEnd(event.getDateStart(), event.getDateEnd()));
        eventDao.insertEvent(event);
        if (event.getPeriodicity() != null) scheduleEventNotificationJob(event);
    }

    @Override
    public Event getEvent(Long eventId) {
        return setDateEnd(eventDao.getEvent(eventId));
    }

    @Override
    public List<Event> eventList() {
        return setDateEnd(eventDao.eventList());
    }

    @Override
    public List<Event> findPrivateEvents(Long userId) {
        return setDateEnd(eventDao.findPrivateEvents(userId));
    }

    @Override
    public List<Event> findAvailableEvents() {
        return setDateEnd(eventDao.findAvailableEvents(userService.getAuthenticatedUser().getId()));
    }

    @Override
    public List<Event> findDrafts(Long userId) {
        return setDateEnd(eventDao.findDrafts(userId));
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeDao.getAllEventTypes();
    }

    @Override
    public void setPersonId(Event event) {
        event.setCreator(userService.getAuthenticatedUser().getId());
    }

    @Override
    public List<Event> getAllMyEvents() {
        return setDateEnd(eventDao.getAllMyEvents(userService.getAuthenticatedUser().getId()));
    }

    @Override
    public void participate(Long userId, Long eventId) {
        eventDao.participate(userId, eventId);
    }

    @Override
    public Long countParticipants(Long eventId) {
        return eventDao.getParticipantsCount(eventId);
    }

    @Override
    public List<User> getParticipants(Long eventId) {
        return eventDao.getParticipants(eventId);
    }

    @Override
    public Boolean isParticipated(Long id, Long eventId) {
        return eventDao.isParticipated(id, eventId) != null;
    }

    @Override
    public void unsubscribe(Long id, Long eventId) {
        eventDao.unsubscribe(id, eventId);
    }

    /**
     * This method checks event access.
     *
     * @param personId
     * @param eventId
     * @return Boolean
     */
    @Override
    public Boolean allowAccess(Long personId, Long eventId) {
        boolean access = false;
        switch (Math.toIntExact(eventDao.getEventType(eventId))) {
            case 0:
                access = isCreator(personId, eventId);
                break;//indefinite
            case 1: // private
                access = isCreator(personId, eventId);
                break;
            case 2: // public
                access = true;
                break;
            case 3: // for friends
                access = friendService.getFriendshipById(personId, getCreatorId(eventId)) != null || isCreator(personId, eventId);
                break;
        }
        return access;
    }

    public Boolean isCreator(Long personId, Long eventId) {
        return eventDao.checkCreatorById(personId, eventId) != null;
    }

    private long getCreatorId(Long eventId) {
        return eventDao.getCreator(eventId).getId();
    }

    @Override
    public void setPriority(Long priority, Long eventId, Long userId) {
        priorityDao.setPriorityToEvent(priority, eventId, userId);
    }

    @Override
    public List<Event> myEventsWithPriority() {
        return setDateEnd(eventDao.listEventsWithPriority(userService.getAuthenticatedUser().getId()));
    }

    @Override
    public Event getMyEventWithPriority(Long eventId) {
        return setDateEnd(eventDao.getEventWithPriority(userService.getAuthenticatedUser().getId(),
                eventId));
    }

    @Override
    public Participant getParticipation(Long eventId) {
        return priorityDao.getParticipant(eventId, userService.getAuthenticatedUser().getId());
    }

    @Override
    public List<Priority> getAllPriorities() {
        return priorityDao.getAllPriority();
    }

    @Override
    public List<Event> findCreatedFriendsEvents(Long id) {
        return setDateEnd(eventDao.findCreatedFriendsEvents(id));
    }

    @Override
    public List<Event> findCreatedPublicEvents(Long id) {
        return setDateEnd(eventDao.findCreatedPublicEvents(id));
    }

    @Override
    public List<User> getFriendsToInvite(Long id, Long eventId) {
        return eventDao.getFriendsToInvite(id, eventId);
    }

    @Override
    public List<User> getUsersToInvite(Long currentId, Long eventId) {
        return eventDao.getUsersToInvite(currentId, eventId);
    }

    @Override
    @Transactional
    public void transferNoteToEvent(Long noteId, Long userId, Event event) {
        noteDao.delete(noteId);
        event.setCreator(userId);
        insertEvent(event);
        if (event.getDraft().equals(false)) {
            eventDao.participate(userId, event.getEventId());
        }
    }

    @Override
    @Transactional
    public void convertDraftToEvent(Long eventId) {
        Event event = eventDao.getEvent(eventId);
        event.setDraft(false);
        eventDao.convertDraftToEvent(event);
    }

    @Override
    public List<Event> getTimelines(Long id) {
        return setDateEnd(eventDao.getAllPersonEvents(id));
    }

    /**
     * This method copies the event.
     *
     * @param toCopy
     * @return Event
     */
    @Override
    public Event copyEvent(Event toCopy) {
        Event event = new Event();
        event.setEventId(toCopy.getEventId());
        event.setName(toCopy.getName());
        event.setDescription(toCopy.getDescription());
        event.setCreator(toCopy.getCreator());
        event.setDateStart(toCopy.getDateStart());
        event.setDateEnd(toCopy.getDateEnd());
        event.setDuration(toCopy.getDuration());
        event.setEndRepeat(toCopy.getEndRepeat());
        event.setPeriodicity(toCopy.getPeriodicity());
        event.setType(toCopy.getType());
        event.setDraft(toCopy.getDraft());
        event.setTypeId(toCopy.getTypeId());
        event.setWidth(toCopy.getWidth());
        event.setLongitude(toCopy.getLongitude());
        event.setEventPlaceName(toCopy.getEventPlaceName());
        event.setPhoto(toCopy.getPhoto());
        event.setPriorityId(toCopy.getPriorityId());
        return event;
    }

    //TODO string values into properties
    private void scheduleEventNotificationJob(Event event) {
        final String EVENT_NOTIFICATION_TRIGGER_GROUP_NAME = "eventNotificationTriggers";
        final String EVENT_NOTIFICATION_TRIGGER_NAME_PREFIX = "eventNotificationTrigger_";
        final String EVENT_NOTIFICATION_JOB_GROUP_NAME = "eventNotificationJobs";
        final String EVENT_NOTIFICATION_JOB_NAME_PREFIX = "eventNotificationJob_";
        final String EMAIL_CONSTRUCTOR_FIELD_NAME = "emailConstructor";
        final String EVENT_FIELD_NAME = "event";
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL_CONSTRUCTOR_FIELD_NAME, emailConstructor);
        params.put(EVENT_FIELD_NAME, event);
        jobSchedulingManager.scheduleJob(event.getEventId(), params, EventNotificationJob.class,
                parseStringToDate(event.getDateStart()), parseStringToDate(event.getEndRepeat()), event.getPeriodicity(),
                EVENT_NOTIFICATION_JOB_NAME_PREFIX, EVENT_NOTIFICATION_JOB_GROUP_NAME,
                EVENT_NOTIFICATION_TRIGGER_NAME_PREFIX, EVENT_NOTIFICATION_TRIGGER_GROUP_NAME);
    }


    //TODO string values into properties
    private void deleteEventNotificationJob(Long eventId) {
        final String EVENT_NOTIFICATION_JOB_GROUP_NAME = "eventNotificationJobs";
        final String EVENT_NOTIFICATION_JOB_NAME_PREFIX = "eventNotificationJob_";
        jobSchedulingManager.deleteJob(eventId, EVENT_NOTIFICATION_JOB_NAME_PREFIX, EVENT_NOTIFICATION_JOB_GROUP_NAME);
    }

    @Override
    public Long getDurationFromStartAndEnd(String start, String end) {
        if (start.equals("____-__-__ __:__") || end.equals("____-__-__ __:__")) {
            return null;
        } else
            return (Utilities.parseStringToTimestamp(end).getTime() - Utilities.parseStringToTimestamp(start).getTime()) / 1000;
    }

    private Event setDateEnd(Event event) {
        if (event.getDateStart() == null) {
            return event;
        }
        event.setDateEnd(getEndDateFromDuration(event.getDateStart(), event.getDuration()));
        return event;
    }

    private List<Event> setDateEnd(List<Event> events) {
        for (Event event : events) {
            setDateEnd(event);
        }
        return events;
    }

    @Override
    public String getEndDateFromDuration(String start, Long duration) {
        return Utilities.parseDateToStringWithSeconds(Utilities.parseLongToDate(Utilities.parseStringToTimestamp(start).getTime() / 1000 + duration));
    }

    @Override
    public String getEndDateFromDuration(Timestamp start, Long duration) {
        return Utilities.parseDateToStringWithSeconds(Utilities.parseLongToDate(start.getTime() / 1000 + duration));
    }

    @Override
    public List<Event> searchByUserFromRange(Long userId, Timestamp start, Timestamp end) {
        return setDateEnd(eventDao.searchByUserFromRange(userId, start, end));
    }

    @Override
    public List<Event> searchByUserFromRange(Long userId, Long start, Long end) {
        return searchByUserFromRange(userId,
                Utilities.parseLongToTimestamp(start),
                Utilities.parseLongToTimestamp(end));
    }

    @Override
    public List<Event> getEventsFromRange(Timestamp fromDate, Timestamp tillDate, Long id) {
        return eventDao.getEventsFromRange(fromDate, tillDate, id);
    }

    @Override
    @Transactional
    public void updateNotificationSchedule(User user) {
        userDao.updateNotificationsSchedule(user);
        deletePersonalPlanNotificationJob(user.getId());
        schedulePersonalPlanNotificationsJob(user);
    }

    @Override
    @Transactional
    public void disableNotifications(Long userId) {
        userDao.disableNotifications(userId);
        deletePersonalPlanNotificationJob(userId);
    }

    private void schedulePersonalPlanNotificationsJob(User user) {
        final String PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX = "personalPlanNotificationJob_";
        final String PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME = "personalPlanNotificationJobs";
        final String PERSONAL_PLAN_NOTIFICATION_TRIGGER_NAME_PREFIX = "personalPlanNotificationTrigger_";
        final String PERSONAL_PLAN_NOTIFICATION_TRIGGER_GROUP_NAME = "personalPlanNotificationTriggers";
        final String EMAIL_CONSTRUCTOR_FIELD_NAME = "emailConstructor";
        final String USER_FIELD_NAME = "user";
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL_CONSTRUCTOR_FIELD_NAME, emailConstructor);
        params.put(USER_FIELD_NAME, userService.getAuthenticatedUser());
        jobSchedulingManager.scheduleJob(user.getId(), params, PersonalPlanNotificationJob.class,
                parseStringToDate(user.getNotificationStartDate()), parseStringToDate(user.getNotificationEndDate()),
                user.getNotificationPeriodicity(),
                PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME,
                PERSONAL_PLAN_NOTIFICATION_TRIGGER_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_TRIGGER_GROUP_NAME);
    }

    private void deletePersonalPlanNotificationJob(Long userId) {
        final String PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX = "personalPlanNotificationJob_";
        final String PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME = "personalPlanNotificationJobs";
        jobSchedulingManager.deleteJob(userId, PERSONAL_PLAN_NOTIFICATION_JOB_NAME_PREFIX, PERSONAL_PLAN_NOTIFICATION_JOB_GROUP_NAME);
    }
}