package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.*;
import com.gmail.netcracker.application.dto.model.*;
import com.gmail.netcracker.application.service.interfaces.*;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.Utilities;
import com.gmail.netcracker.application.utilites.scheduling.JobSchedulingManager;
import com.gmail.netcracker.application.utilites.scheduling.jobs.EventNotificationJob;
import com.gmail.netcracker.application.utilites.scheduling.jobs.PersonalPlanNotificationJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.gmail.netcracker.application.utilites.Utilities.parseStringToDate;

/**
 * This class is a event service which connects DAO layer and controller.
 */

@Service
@PropertySource(value = "classpath:quartz_constants.properties")
public class EventServiceImpl implements EventService {
    @Autowired
    private EventDao eventDao;
    @Autowired
    private EventTypeDao eventTypeDao;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private PriorityDao priorityDao;
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChatService chatService;
    @Autowired
    private PhotoServiceImp photoService;
    @Autowired
    private JobSchedulingManager jobSchedulingManager;
    @Autowired
    private EmailConstructor emailConstructor;
    @Autowired
    private Environment env;

    @Override
    @Transactional
    public void update(Event event) {
        setEventCreator(event);
        event.setDuration(getDurationFromStartAndEnd(event.getDateStart(), event.getDateEnd()));
        Logger.getLogger(EventServiceImpl.class.getName()).info(event.toString());
        eventDao.update(event);
        deleteEventNotificationJob(event.getEventId());
        if (event.getPeriodicity() != null && !event.getDraft()) {
            scheduleEventNotificationJob(event);
        }
    }

    /**
     * Deletes event, disables event notifications, deletes event photo and cancels items booking,
     * which was booked from this event
     */
    @Override
    @Transactional
    public void delete(Long eventId) {
        Event event = getEvent(eventId);
        itemDao.cancelItemsBookingFromEvent(eventId);
        if (!event.getPhoto().equals(photoService.getDefaultImageForEvents())) {
            photoService.deleteFile(event.getPhoto());
        }
        eventDao.delete(eventId);
        if (!event.getDraft()) {
            deleteEventNotificationJob(eventId);
        }
        ;
    }

    /**
     * Inserts event, creates two chats for this event: with creator and without creator
     * and adds creator as participant to event.
     * If event is periodical, event notifications will be enabled
     */
    @Override
    @Transactional
    public void insertEvent(Event event) {
        setEventCreator(event);
        event.setDuration(getDurationFromStartAndEnd(event.getDateStart(), event.getDateEnd()));
        eventDao.insertEvent(event);
        if (event.getType().equals(2L) || event.getType().equals(3L)
                && !event.getDraft()) {
            chatService.createChatForEvent(event, true);
            chatService.createChatForEvent(event, false);
        }
        if (!event.getDraft()) {
            participate(userService.getAuthenticatedUser().getId(), event.getEventId());
            if (event.getPeriodicity() != null)
                scheduleEventNotificationJob(event);
        }
    }

    /**
     * Finds event by id
     *
     * @return Event with eventId or null if such event doesn't exist
     */
    @Override
    @Transactional(readOnly = true)
    public Event getEvent(Long eventId) {
        return setDateEnd(eventDao.getEvent(eventId));
    }

    /**
     * Finds private events of user with userId
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findPrivateEvents(Long userId) {
        return setDateEnd(eventDao.findPrivateEvents(userId));
    }

    /**
     * Finds public and friend events for authenticated user
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findAvailableEvents() {
        return setDateEnd(eventDao.findAvailableEvents(userService.getAuthenticatedUser().getId()));
    }

    /**
     * Finds user drafts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findDrafts(Long userId) {
        return setDateEnd(eventDao.findDrafts(userId));
    }

    /**
     * Get all event types
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventType> getAllEventTypes() {
        return eventTypeDao.getAllEventTypes();
    }

    /**
     * Sets authenticated user as event creator
     */
    @Override
    public void setEventCreator(Event event) {
        event.setCreator(userService.getAuthenticatedUser().getId());
    }

    /**
     * Finds events which user is subscribed
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findEventSubscriptions() {
        return setDateEnd(eventDao.findEventSubscriptions(userService.getAuthenticatedUser().getId()));
    }

    /**
     * Subscribes user for event
     */
    @Override
    @Transactional
    public void participate(Long userId, Long eventId) {
        eventDao.participate(userId, eventId);
    }

    /**
     * Count participants in event
     */
    @Override
    @Transactional(readOnly = true)
    public Long countParticipants(Long eventId) {
        return eventDao.getParticipantsCount(eventId);
    }

    /**
     * Gets participants of event
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getParticipants(Long eventId) {
        return eventDao.getParticipants(eventId);
    }

    /**
     * Checks is user participant of event
     *
     * @return true if user is participant of this event, else false
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean isParticipantOfEvent(Long userId, Long eventId) {
        return eventDao.isParticipantOfEvent(userId, eventId) != null;
    }

    /**
     * Unsubscribes user from event
     */
    @Override
    @Transactional
    public void unsubscribe(Long userId, Long eventId) {
        eventDao.unsubscribe(userId, eventId);
    }

    /**
     * Checks if user has access to event
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean allowAccess(Long userId, Long eventId) {
        Event event = eventDao.getEvent(eventId);
        Boolean access = false;
        switch (Math.toIntExact(event.getTypeId())) {
            case 0:
                access = userId.equals(event.getCreator());
                break;//indefinite
            case 1: // private
                access = userId.equals(event.getCreator());
                break;
            case 2: // public
                access = true;
                break;
            case 3: // for friends
                access = userId.equals(event.getCreator()) ||
                        (friendService.getFriendshipById(userId, event.getCreator()) != null);
                break;
        }
        return access;
    }

    /**
     * Checks if user is creator of event
     */
    @Transactional(readOnly = true)
    public Boolean isCreator(Long personId, Long eventId) {
        return eventDao.checkCreatorById(personId, eventId) != null;
    }

    /**
     * Sets event priority for user
     */
    @Override
    @Transactional
    public void setPriority(Long priority, Long eventId, Long userId) {
        priorityDao.setPriorityToEvent(priority, eventId, userId);
    }

    /**
     * Finds list of events, which authenticated user is subscribed for
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> myEventsWithPriority() {
        return setDateEnd(eventDao.listEventsWithPriority(userService.getAuthenticatedUser().getId()));
    }

    /**
     * Finds one event with priority
     */
    @Override
    @Transactional(readOnly = true)
    public Event findEventSubscriptionsWithPriority(Long eventId) {
        return setDateEnd(eventDao.findEventSubscriptionsWithPriority(userService.getAuthenticatedUser().getId(),
                eventId));
    }

    /**
     * @return participation with eventId and priorityId
     */
    @Override
    @Transactional(readOnly = true)
    public Participant getParticipation(Long eventId) {
        return priorityDao.getParticipant(eventId, userService.getAuthenticatedUser().getId());
    }

    /**
     * @return All priorities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Priority> getAllPriorities() {
        return priorityDao.getAllPriority();
    }

    /**
     * @return Managed events with type "only for friends"
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findCreatedFriendsEvents(Long id) {
        return setDateEnd(eventDao.findCreatedFriendsEvents(id));
    }

    /**
     * @return Managed events with type "public"
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findCreatedPublicEvents(Long id) {
        return setDateEnd(eventDao.findCreatedPublicEvents(id));
    }

    /**
     * @return List of users friends which are not participants of this event
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findFriendsForInvite(Long authUserId, Long eventId) {
        return eventDao.findFriendsForInvite(authUserId, eventId);
    }

    /**
     * @return List of all users, which are not participants of this event
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findUserForInvite(Long authUserId, Long eventId) {
        return eventDao.findUsersForInvite(authUserId, eventId);
    }

    /**
     * Converts note to event
     *
     * @param event
     */
    @Override
    @Transactional
    public void convertNoteToEvent(Long noteId, Long userId, Event event) {
        noteDao.delete(noteId);
        event.setCreator(userId);
        insertEvent(event);
    }

    /**
     * Converts draft to event
     */
    @Override
    @Transactional
    public void convertDraftToEvent(Event event) {
        event.setDraft(false);
        Logger.getLogger(EventServiceImpl.class.getName()).info(event.toString());
        eventDao.update(event);
        if (event.getType().equals(2L) || event.getType().equals(3L) && !event.getDraft()) {
            chatService.createChatForEvent(event, true);
            chatService.createChatForEvent(event, false);
        }

        participate(userService.getAuthenticatedUser().getId(), event.getEventId());
        if (event.getPeriodicity() != null) scheduleEventNotificationJob(event);
    }

    /**
     * @return Events in which user is participant.
     */
    @Override
    @Transactional(readOnly = true)
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

    private void scheduleEventNotificationJob(Event event) {
        Map<String, Object> params = new HashMap<>();
        params.put(env.getRequiredProperty("emailConstructor.fieldName"), emailConstructor);
        params.put(env.getRequiredProperty("event.fieldName"), event);
        jobSchedulingManager.scheduleJob(event.getEventId(), params, EventNotificationJob.class,
                parseStringToDate(event.getDateStart()), parseStringToDate(event.getEndRepeat()), event.getPeriodicity(),
                env.getProperty("eventNotification.job.namePrefix"), env.getProperty("eventNotification.job.groupName"),
                env.getProperty("eventNotification.trigger.namePrefix"), env.getProperty("eventNotification.trigger.groupName"));
    }

    private void deleteEventNotificationJob(Long eventId) {
        jobSchedulingManager.deleteJob(eventId, env.getProperty("eventNotification.job.namePrefix"), env.getProperty("eventNotification.job.groupName"));
    }

    @Override
    public Long getDurationFromStartAndEnd(String start, String end) {
        if ("____-__-__ __:__".equals(start) || "____-__-__ __:__".equals(end) || "".equals(start) || "".equals(end)) {
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
        Map<String, Object> params = new HashMap<>();
        params.put(env.getRequiredProperty("emailConstructor.fieldName"), emailConstructor);
        params.put(env.getRequiredProperty("user.fieldName"), userService.getAuthenticatedUser());
        jobSchedulingManager.scheduleJob(user.getId(), params, PersonalPlanNotificationJob.class,
                parseStringToDate(user.getNotificationStartDate()), parseStringToDate(user.getNotificationEndDate()),
                user.getNotificationPeriodicity(),
                env.getProperty("personalPlanNotification.job.namePrefix"), env.getProperty("personalPlanNotification.job.groupName"),
                env.getProperty("personalPlanNotification.trigger.namePrefix"), env.getProperty("personalPlanNotification.trigger.groupName"));
    }

    private void deletePersonalPlanNotificationJob(Long userId) {
        jobSchedulingManager.deleteJob(userId, env.getProperty("personalPlanNotification.job.namePrefix"), env.getProperty("personalPlanNotification.job.groupName"));
    }
}
