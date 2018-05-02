package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository//TODO добавить priority в запросы
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
    private String SQL_FIND_PERSON_EVENTS;//TODO не учитывает isDraft;

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

    private final RowMapper<Event> rowMapper;
    private final RowMapper<User> participantRowMapper;


    @Autowired
    public EventDaoImpl(DataSource dataSource,
                        @Qualifier("eventRowMapper") RowMapper<Event> rowMapper,
                        @Qualifier("friendRowMapper") RowMapper<User> participantRowMapper) {
        super(dataSource);
        this.rowMapper = rowMapper;
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
        return findEntity(SQL_FIND, rowMapper, eventId);
    }

    @Override
    public List<Event> eventList() {
        return findEntityList(SQL_FIND_LIST_BY_CREATOR, rowMapper);
    }

    @Override
    public List<Event> findPublicEvents() {
        return findEntityList(SQL_FIND_PUBLIC_EVENTS, rowMapper);
    }

    @Override
    public List<Event> findPrivateEvents(Long userId) {
        return findEntityList(SQL_FIND_PRIVATE_EVENTS, rowMapper, userId);
    }

    @Override
    public List<Event> findFriendsEvents(Long userId) {
        return findEntityList(SQL_FIND_FRIENDS_EVENTS, rowMapper, userId, userId);
    }

    @Override
    public List<Event> findDrafts(Long userId) {
        return findEntityList(SQL_FIND_DRAFTS, rowMapper, userId);
    }

    @Override
    public List<Event> getAllMyEvents(Long personId) {
        return findEntityList(SQL_FIND_PERSON_EVENTS, rowMapper, personId);
    }

    @Override
    public void participate(Long user_id, long event_id) {
        updateEntity(SQL_PARTICIPATE, user_id, event_id);
    }

    @Override
    public int getParticipantsCount(int eventId) {
        return countRows(SQL_COUNT_PARTICIPANTS, eventId);
    }

    @Override
    public List<User> getParticipants(long event_id) {
        return findEntityList(SQL_GET_PARTICIPANTS, participantRowMapper, event_id);
    }

    @Override
    public int isParticipated(Long id, int eventId) {
        return countRows(SQL_IS_PARTICIPATED, id, eventId);
    }

    @Override
    public void unsubscribe(long id, long event_id) {
        deleteEntity(SQL_UNSUBSCRIBE, id, event_id);
    }
}

