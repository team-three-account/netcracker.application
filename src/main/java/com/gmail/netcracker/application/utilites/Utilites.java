package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilites {

    public static Timestamp parseStringIntoDate(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = formatter.parse(str_date);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseDateIntoString(Date date) {
        if (date == null) {
            return "";
        }
        Format df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public static Timestamp parseTime(String str_date) {
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

    public static int parseStringToInt(String srt) {
        int value = 0;
        try {
            value = Integer.parseInt(srt);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return value;
    }

    public static final class EventMapper implements RowMapper<Event> {
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
}
