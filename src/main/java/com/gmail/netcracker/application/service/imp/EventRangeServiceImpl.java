package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.dao.interfaces.UserDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventRangeService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.utilites.Utilities;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EventRangeServiceImpl implements EventRangeService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventService eventService;

    private Logger log = Logger.getLogger(EventRangeServiceImpl.class.getName());

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsFromRange(Long userId, Long start, Long end) {
        return getEventsFromRange(userId, Utilities.parseLongToTimestamp(start),
                Utilities.parseLongToTimestamp(end));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsFromRange(Long userId, Timestamp start, Timestamp end) {
        List<Event> eventList = new ArrayList<>();

        List<Event> listWithoutPeriodicity = eventDao.searchByUserFromRange(userId, start, end);
        for (Event event : listWithoutPeriodicity)
            if (event.getPeriodicity() == null)
                eventList.add(event);
            else eventList.addAll(getAllDateFromPeriodical(event, start, end));
        return eventList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsFromRange(List<Long> userList, Long start, Long end) {
        List<Event> eventList = new ArrayList<>();
        for (Long userId: userList){
            List<Event> userEvents = getEventsFromRange(userId, start, end);
            User user = userDao.findUserById(userId);
            for (Event userEvent: userEvents) {
                userEvent.setName(user.getName()+" "+user.getSurname());
            }
            eventList.addAll(userEvents);
        }
        return eventList;
    }

    /**
     *
     * @param event Event what we duplicate
     * @param start startRange
     * @param end endRange
     * @return
     */
    private List<Event> getAllDateFromPeriodical(Event event,
                                                 Date start, Date end) {
        try {
            List<Event> eventList = new ArrayList<>();
            CronExpression cronExpression = new CronExpression(event.getPeriodicity());
            Date startEvent = Utilities.parseStringToTimestamp(event.getDateStart());
            Date endRepeat = Utilities.parseStringToTimestamp(event.getEndRepeat());

            Boolean condition = true;
            while (condition){
                if(startEvent != null && (endRepeat == null || startEvent.before(endRepeat))) {
                    Date endEvent = Utilities.parseLongToDate(startEvent.getTime()/1000 + event.getDuration());

                    if (endEvent.before(start)) {

                        startEvent = cronExpression.getNextValidTimeAfter(startEvent);

                    } else if (startEvent.before(end)) {

                        Event eventCopy = eventService.copyEvent(event);
                        eventCopy.setDateStart(Utilities.parseDateToStringWithSeconds(startEvent));
                        eventCopy.setDateEnd(Utilities.parseDateToStringWithSeconds(endEvent));
                        eventList.add(eventCopy);

                        startEvent = cronExpression.getNextValidTimeAfter(startEvent);

                    } else condition = false;
                }
                else condition = false;
            }

            return eventList;

        } catch (ParseException e) {
            log.info("Periodicity not cron!!!");
            return new ArrayList<>();
        }
    }
}
