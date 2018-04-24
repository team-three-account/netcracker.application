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
    private static final String EVENT_READ = "SELECT event_id, \"Event\".name, description, email, start_date, end_date, place_id, place_address, periodicity, valueee, is_draft, folder\n" +
            "FROM public.\"Event\"\n" +
            "INNER JOIN \"Type\" ON \"Event\".type=\"Type\".type_id\n" +
            "INNER JOIN \"person\" ON \"Event\".creator=\"person\".person_id";

    private static final String EVENT_INSERT = "INSERT INTO public.\"Event\"(\n" +
            "\t name, description,creator, start_date, end_date, place_id, place_address, periodicity, type, is_draft, folder)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String EVENT_DELETE = "DELETE FROM public.\"Event\"\n" +
            "\tWHERE \"Event\".event_id=?";
    private static final String GET_ALL_EVENT_TYPES = "SELECT type_id, valueee\n" +
            "\tFROM public.\"Type\";";


    @Override
    public void update(Event event) {

    }

    @Override
    public void delete(int eventId) {
        jdbcTemplate.update(EVENT_DELETE, eventId);
    }

    @Override
    public void insertEvent(Event event) {

        jdbcTemplate.update(EVENT_INSERT,
                event.getName(),
                event.getDescription(),
                event.getCreator(),
                parseTime(event.getDateStart()),
                parseTime(event.getDateEnd()),
                event.getPlaceId(),
                event.getPlaceAddress(),
                event.getPeriodicity(),
                parseStringToInt(event.getType()),
                event.isDraft(),
                event.getFolder());
    }

    @Override
    public List<Event> eventList() {
        List<Event> listEmployee = jdbcTemplate.query(EVENT_READ, new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setName(resultSet.getString("name"));
                event.setDescription(resultSet.getString("description"));
                event.setCreator(resultSet.getString("email"));
                event.setDateStart(resultSet.getString("start_date"));
                event.setDateEnd(resultSet.getString("end_date"));
                event.setPlaceId(resultSet.getString("place_id"));
                event.setPlaceAddress(resultSet.getString("place_address"));
                event.setPeriodicity(resultSet.getInt("periodicity"));
                event.setType(resultSet.getString("valueee"));
                event.setDraft(resultSet.getBoolean("is_draft"));
                event.setFolder(resultSet.getInt("folder"));
                return event;
            }

        });

        return listEmployee;
    }


    @Override
    public List<Event> findAllEventTypes() {
        List<Event> listEventTypes = jdbcTemplate.query(GET_ALL_EVENT_TYPES, (rs, rowNum) -> {
            Event eventType = new Event();
            eventType.setTypeId(rs.getInt("type_id"));
            eventType.setType(rs.getString("valueee"));
            return eventType;
        });
        return listEventTypes;
    }

    private Timestamp parseTime(String str_date) {
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

    private int parseStringToInt(String srt) {
        int example = Integer.parseInt(srt);
        return example;
    }
}
