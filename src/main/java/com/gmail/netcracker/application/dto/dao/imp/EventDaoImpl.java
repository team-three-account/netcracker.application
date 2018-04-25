package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class EventDaoImpl extends ModelDao implements EventDao {
    private static final String EVENT_READ = "SELECT event_id, \"Event\".name, description, person_id, start_date, end_date, value\n" +
            "FROM public.\"Event\"\n" +
            "INNER JOIN \"Type\" ON \"Type\".type_id=\"Event\".type\n" +
            "INNER JOIN \"Person\" ON \"Person\".person_id = \"Event\".creator";

    private static final String EVENT_INSERT = "INSERT INTO public.\"Event\"( name, description, creator, start_date," +
            " end_date, width, longitude, eventplacename, type, is_draft)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String EVENT_DELETE = "DELETE FROM public.\"Event\"\n" +
            "\tWHERE \"Event\".event_id=?";

    private static final String GET_ALL_EVENT_TYPES = "SELECT type_id, value\n" +
            "\tFROM public.\"Type\";";

    private static final String EVENT_UPDATE = "UPDATE public.\"Event\" SET name=?, description=?," +
            "start_date=?,end_date=?, type=?, is_draft=?,width=?,longitude=?,eventplacename=?" +
            "WHERE event_id=?";
    private static final String GET_EVENT_BY_ID = "SELECT  event_id,name, description,creator, start_date, end_date,\n" +
            " type, is_draft, folder,width,longitude,eventplacename,periodicity\n" +
            "FROM public.\"Event\"\n" +
            "where event_id=";

    @Override
    public void update(Event event) {
        if (event.getEventId() > 0) {
            jdbcTemplate.update(EVENT_UPDATE, event.getName(), event.getDescription(),
                    parseTime(event.getDateStart()), parseTime(event.getDateEnd()), parseStringToInt(event.getType()),
                    event.isDraft(), event.getWidth(), event.getLongitude(),
                    event.getEventPlaceName(), event.getEventId());
        }
    }

    @Override
    public void delete(int eventId) {
        jdbcTemplate.update(EVENT_DELETE, eventId);
    }

    @Override
    public void insertEvent(Event event) {
        // evventid.UUAID.toString();

        jdbcTemplate.update(EVENT_INSERT, event.getName(), event.getDescription(), event.getCreator(),
                parseTime(event.getDateStart()), parseTime(event.getDateEnd()),
                event.getWidth(), event.getLongitude(), event.getEventPlaceName(),
                parseStringToInt(event.getType()), event.isDraft());
    }

    @Override
    public Event getEvent(int eventId) {
        String getEvent = GET_EVENT_BY_ID + eventId;
        return jdbcTemplate.query(getEvent, rs -> {
            if (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setName(rs.getString("name"));
                event.setDescription(rs.getString("description"));
                event.setCreator(rs.getLong("creator"));
                event.setDateStart(rs.getString("start_date"));
                event.setDateEnd(rs.getString("end_date"));
                event.setType(rs.getString("type"));
                event.setDraft(rs.getBoolean("is_draft"));
                event.setFolder(rs.getInt("folder"));
                event.setWidth(rs.getDouble("width"));
                event.setLongitude(rs.getDouble("longitude"));
                event.setEventPlaceName(rs.getString("eventplacename"));
                event.setPeriodicity(rs.getInt("periodicity"));
                return event;
            }
            return null;
        });
    }

    @Override
    public List<Event> eventList() {
        List<Event> listEmployee = jdbcTemplate.query(EVENT_READ, new EventMapper());
        return listEmployee;
    }


    @Override
    public List<Event> findAllEventTypes() {
        List<Event> listEventTypes = jdbcTemplate.query(GET_ALL_EVENT_TYPES, (rs, rowNum) -> {
            Event eventType = new Event();
            eventType.setTypeId(rs.getInt("type_id"));
            eventType.setType(rs.getString("value"));
            return eventType;
        });
        return listEventTypes;
    }

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

    private int parseStringToInt(String srt) {
        int value = 0;
        try {
            value = Integer.parseInt(srt);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return value;
    }
}

final class EventMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int i) throws SQLException {
        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setName(rs.getString("name"));
        event.setCreator(rs.getLong("person_id"));
        event.setDateStart(rs.getString("start_date"));
        event.setDateEnd(rs.getString("end_date"));
        event.setType(rs.getString("value"));
        return event;
    }
}
