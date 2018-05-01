package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//TODO fix problem with inserting drafts
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

    private final RowMapper<Event> rowMapper;

    @Autowired
    public EventDaoImpl(DataSource dataSource,
                        @Qualifier("eventRowMapper") RowMapper<Event> rowMapper) {
        super(dataSource);
        this.rowMapper = rowMapper;
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
                event.isDraft());
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
        return findEntityList(SQL_FIND_FRIENDS_EVENTS, rowMapper, userId, userId, userId);
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
}

