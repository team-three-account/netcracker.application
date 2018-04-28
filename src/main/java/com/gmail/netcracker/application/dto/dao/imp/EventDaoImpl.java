package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final RowMapper<Event> eventRowMapper;
    private final RowMapper<Event> eventTypeRowMapper;

    @Autowired
    protected EventDaoImpl(DataSource dataSource,
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
                parseTime(event.getDateStart()),
                parseTime(event.getDateEnd()),
                Utilities.parseStringToInt(event.getType()),
                event.isDraft(),
                event.getWidth(),
                event.getLongitude(),
                event.getEventPlaceName(),
                event.getEventId());
    }

    @Override
    public void delete(int eventId) {
        deleteEntity(SQL_DELETE, (long) eventId);
    }

    @Override
    public void insertEvent(Event event) {
        insertEntity(SQL_ADD, PK_COLUMN_NAME,
                event.getName(),
                event.getDescription(),
                event.getCreator(),
                parseTime(event.getDateStart()),
                parseTime(event.getDateEnd()),
                event.getWidth(),
                event.getLongitude(),
                event.getEventPlaceName(),
                Utilities.parseStringToInt(event.getType()),
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

    //TODO move this method to Utilities (there is problem with date format)
    private Timestamp parseTime(String str_date) {
        if (str_date != null) {
            try {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = formatter.parse(str_date);
                Timestamp timestamp = new Timestamp(date.getTime());
                return timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}