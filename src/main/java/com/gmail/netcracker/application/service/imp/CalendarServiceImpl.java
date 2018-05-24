package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.CalendarService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.utilites.Utilities;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventService eventService;

    private Logger log = Logger.getLogger(CalendarServiceImpl.class.getName());


    @Override
    public List<Event> getEventsFromRange(User user, Long start, Long end) {
        return getEventsFromRange(user.getId(), start, end);
    }

    @Override
    public List<Event> getEventsFromRange(Long userId, Long start, Long end) {
        List<Event> eventList = new ArrayList<>();

        for (Event event : eventDao.searchByUserFromRange(userId,
                Utilities.parseLongToTimestamp(start),
                Utilities.parseLongToTimestamp(end))) {
            if ((event.getPeriodicity() == null))
                eventList.add(event);
            else eventList.addAll(getAllDateFromPeriodical(event,
                    Utilities.parseLongToDate(start),
                    Utilities.parseLongToDate(end)));

        }
        return eventList;
    }

    private List<Event> getAllDateFromPeriodical(Event event,
                                                 Date start, Date end) {
        try {
            CronExpression cronExpression = new CronExpression(event.getPeriodicity());
            return getAllDateFromPeriodical(new ArrayList<>(), event,
                    Utilities.parseStringToTimestamp(event.getDateStart()),
                    cronExpression, start, end);


        } catch (ParseException e) {
            log.info("Periodicity not cron!!!");
            return new ArrayList<>();
        }
    }

    private List<Event> getAllDateFromPeriodical(List<Event> result,
                                                 Event event,
                                                 Date startEvent,
                                                 CronExpression cron,
                                                 Date start, Date end) {
        Date endEvent = Utilities.parseLongToDate(startEvent.getTime() / 1000 + event.getDuration());
        Date nextValidTime = cron.getNextValidTimeAfter(startEvent);
        Date endRepeat = Utilities.parseStringToTimestamp(event.getEndRepeat());
        if (nextValidTime != null || !startEvent.before(endRepeat)) {
            if (endEvent.before(start)) {
                return getAllDateFromPeriodical(result, event, nextValidTime,
                                                cron, start, end);
            } else if (startEvent.before(end)) {
                Event eventCopy = eventService.copyEvent(event);
                eventCopy.setDateStart(Utilities.parseDateToStringWithSeconds(startEvent));
                eventCopy.setDateEnd(Utilities.parseDateToStringWithSeconds(endEvent));
                result.add(eventCopy);
                return getAllDateFromPeriodical(result, event, nextValidTime,
                                                cron, start, end);
            } else return result;
        } else return result;
    }
}
