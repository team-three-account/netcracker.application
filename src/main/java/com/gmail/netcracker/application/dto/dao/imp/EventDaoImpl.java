package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
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
    private final String PK_COLUMN_NAME = "event_id";

    private final String SQL_ADD = "event/add.sql";
    private final String SQL_DELETE = "event/delete.sql";
    private final String SQL_FIND = "event/find.sql";
    private final String SQL_FIND_LIST_BY_CREATOR = "event/findListByCreator.sql";
    private final String SQL_GET_EVENT_TYPES = "event/getEventTypes.sql";
    private final String SQL_UPDATE = "event/update.sql";

    private final RowMapper<Event> eventRowMapper;
    private final RowMapper<Event> eventTypeRowMapper;

    @Autowired
    protected EventDaoImpl(DataSource dataSource, ResourceLoader resourceLoader,
                           Environment environment,
                           @Qualifier("eventRowMapper") RowMapper<Event> eventRowMapper,
                           @Qualifier("eventTypeRowMapper") RowMapper<Event> eventTypeRowMapper) {
        super(dataSource, resourceLoader, environment);
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