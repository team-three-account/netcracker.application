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

@Repository
public class EventDaoImpl extends ModelDao implements EventDao {
    @Value("${sql.event.pkColumnName}")
    private String PK_COLUMN_NAME = "event_id";

    @Value("${sql.event.add}")
    private String SQL_ADD;

    @Value("${sql.event.delete}")
    private String SQL_DELETE;

    @Value("${sql.event.find}")
    private String SQL_FIND;

    @Value("${sql.event.findListByCreator}")
    private String SQL_FIND_LIST_BY_CREATOR;

    @Value("${sql.event.getEventTypes}")
    private String SQL_GET_EVENT_TYPES;

    @Value("${sql.event.update}")
    private String SQL_UPDATE;

    @Value("${sql.event.findPersonEvents}")
    private String SQL_FIND_PERSON_EVENTS;

    @Value("${sql.event.participate}")
    private String SQL_PARTICIPATE;

    private final RowMapper<Event> eventRowMapper;
    private final RowMapper<Event> eventTypeRowMapper;

    @Autowired
    public EventDaoImpl(DataSource dataSource,
                        @Qualifier("eventRowMapper") RowMapper<Event> eventRowMapper,
                        @Qualifier("eventTypeRowMapper") RowMapper<Event> eventTypeRowMapper) {
        super(dataSource);
        this.eventRowMapper = eventRowMapper;
        this.eventTypeRowMapper = eventTypeRowMapper;
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
        return findEntity(SQL_FIND, eventRowMapper, eventId);
    }

    @Override
    public List<Event> eventList() {
        return findEntityList(SQL_FIND_LIST_BY_CREATOR, eventRowMapper);
    }

    @Override
    public List<Event> findAllEventTypes() {
        return findEntityList(SQL_GET_EVENT_TYPES, eventTypeRowMapper);
    }

    @Override
    public List<Event> getAllMyEvents(Long personId) {
        return findEntityList(SQL_FIND_PERSON_EVENTS, eventRowMapper, personId);
    }

    @Override
    public void participate(Long user_id, long event_id) {
        updateEntity(SQL_PARTICIPATE, user_id, event_id);
    }
}

