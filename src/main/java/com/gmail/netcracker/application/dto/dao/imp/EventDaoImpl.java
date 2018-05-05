package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventDaoImpl extends ModelDao implements EventDao {
    @Value("${sql.event.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.event.add}")
    private String SQL_ADD;

    @Value("${sql.event.delete}")
    private String SQL_DELETE;

    @Value("${sql.event.find}")
    private String SQL_FIND;

    @Value("${sql.event.findListByCreator}")
    private String SQL_FIND_LIST_BY_CREATOR;

    @Value("${sql.event.findPublicEvents}")
    private String SQL_FIND_PUBLIC_EVENTS;

    @Value("${sql.event.findPrivateEvents}")
    private String SQL_FIND_PRIVATE_EVENTS;

    @Value("${sql.event.findFriendsEvents}")
    private String SQL_FIND_FRIENDS_EVENTS;

    @Value("${sql.event.findDrafts}")
    private String SQL_FIND_DRAFTS;

    @Value("${sql.event.update}")
    private String SQL_UPDATE;

    @Value("${sql.event.findPersonEvents}")
    private String SQL_FIND_PERSON_EVENTS;

    @Value("${sql.event.participate}")
    private String SQL_PARTICIPATE;

    @Value("${sql.event.countParticipants}")
    private String SQL_COUNT_PARTICIPANTS;

    @Value("${sql.event.getParticipants}")
    private String SQL_GET_PARTICIPANTS;

    @Value("${sql.event.isParticipated}")
    private String SQL_IS_PARTICIPATED;

    @Value("${sql.event.unsubscribe}")
    private String SQL_UNSUBSCRIBE;

    @Value("${sql.event.findCreatedFriendsEvents}")
    private String SQL_FIND_CREATED_FRIENDS_EVENTS;

    @Value("${sql.event.findCreatedPublicEvents}")
    private String SQL_FIND_CREATED_PUBLIC_EVENTS;

    @Value("${sql.event.maxid}")
    private String SQL_MAX_ID;

    @Value("${sql.event.getEventType}")
    private String SQL_GET_EVENT_TYPE;

    @Value("${sql.event.getCreator}")
    private String SQL_GET_CREATOR;

    @Value("${sql.event.checkCreator}")
    private String SQL_CHECK_CREATOR;

    private final RowMapper<Event> eventRowMapper;
    private final RowMapper<User> friendRowMapper;
    private final RowMapper<Participant> participantRowMapper;

    @Autowired
    public EventDaoImpl(DataSource dataSource, RowMapper<Event> eventRowMapper, RowMapper<User> userRowMapper,
                        RowMapper<Participant> participantRowMapper) {
        super(dataSource);
        this.eventRowMapper = eventRowMapper;
        this.friendRowMapper = userRowMapper;
        this.participantRowMapper = participantRowMapper;
    }

    @Override
    public void update(Event event) {
        updateEntity(SQL_UPDATE,
                event.getName(),
                event.getDescription(),
                Utilites.parseTime(event.getDateStart()),
                Utilites.parseTime(event.getDateEnd()),
                Utilites.parseStringToInt(event.getType()),
                event.isDraft(),
                event.getWidth(),
                event.getLongitude(),
                event.getEventPlaceName(),
                event.getPhoto(),
                event.getEventId());
    }

    @Override
    public void delete(int eventId) {
        deleteEntity(SQL_DELETE, eventId);
    }

    @Override
    public void insertEvent(Event event) {
        insertEntity(SQL_ADD, PK_COLUMN_NAME,
                event.getName(),
                event.getDescription(),
                event.getCreator(),
                Utilites.parseTime(event.getDateStart()),
                Utilites.parseTime(event.getDateEnd()),
                event.getWidth(),
                event.getLongitude(),
                event.getEventPlaceName(),
                Utilites.parseStringToInt(event.getType()),
                event.isDraft(),
                event.getPhoto());
    }

    @Override
    public Event getEvent(int eventId) {
        return findEntity(SQL_FIND, eventRowMapper, eventId);
    }

    @Override
    public List<Event> eventList() {
        return findEntityList(SQL_FIND_LIST_BY_CREATOR, eventRowMapper);
    }

    @Override
    public List<Event> findPublicEvents() {
        return findEntityList(SQL_FIND_PUBLIC_EVENTS, eventRowMapper);
    }

    @Override
    public List<Event> findPrivateEvents(Long userId) {
        return findEntityList(SQL_FIND_PRIVATE_EVENTS, eventRowMapper, userId);
    }

    @Override
    public List<Event> findFriendsEvents(Long userId) {
        return findEntityList(SQL_FIND_FRIENDS_EVENTS, eventRowMapper, userId, userId);
    }

    @Override
    public List<Event> findDrafts(Long userId) {
        return findEntityList(SQL_FIND_DRAFTS, eventRowMapper, userId);
    }

    @Override
    public List<Event> getAllMyEvents(Long personId) {
        return findEntityList(SQL_FIND_PERSON_EVENTS, eventRowMapper, personId);
    }

    @Override
    public void participate(Long userId, long eventId) {
        updateEntity(SQL_PARTICIPATE, userId, eventId);
    }

    @Override
    public int getParticipantsCount(int eventId) {
        return countRows(SQL_COUNT_PARTICIPANTS, eventId);
    }

    @Override
    public List<User> getParticipants(long eventId) {
        return findEntityList(SQL_GET_PARTICIPANTS, friendRowMapper, eventId);
    }

    @Override
    public Participant isParticipated(Long id, int eventId) {
        return findEntity(SQL_IS_PARTICIPATED, participantRowMapper, id, eventId);
    }

    @Override
    public void unsubscribe(long id, long eventId) {
        deleteEntity(SQL_UNSUBSCRIBE, id, eventId);
    }

    @Override
    public List<Event> findCreatedFriendsEvents(Long id) {
        return findEntityList(SQL_FIND_CREATED_FRIENDS_EVENTS, eventRowMapper, id);
    }

    @Override
    public List<Event> findCreatedPublicEvents(Long id) {
        return findEntityList(SQL_FIND_CREATED_PUBLIC_EVENTS, eventRowMapper, id);
    }

    @Override
    public int getMaxId() {
        return maxIdValue(SQL_MAX_ID);
    }

    @Override
    public int getEventType(int eventId) {
        return countRows(SQL_GET_EVENT_TYPE, eventId);
    }

    @Override
    public User getCreator(int eventId) {
        return findEntity(SQL_GET_CREATOR, friendRowMapper, eventId);
    }

    @Override
    public Event checkCreatorById(Long personId, int eventId) {
        return findEntity(SQL_CHECK_CREATOR, eventRowMapper, personId, eventId);
    }
}

