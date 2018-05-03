package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ParticipantDao;
import com.gmail.netcracker.application.dto.model.Participant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ParticipantDaoImpl extends ModelDao implements ParticipantDao {

    @Value("${sql.participant.setPriority}")
    private String SQL_SET_PRIORITY;

    @Value("${sql.participant.getEventPriority}")
    private String SQL_GET_EVENT_PRIORITY;

    @Value("${sql.participant.getParticipant}")
    private String SQL_GET_PARTICIPANT;

    private RowMapper<Participant> rowMapper;


    protected ParticipantDaoImpl(DataSource dataSource, RowMapper<Participant> rowMapper) {

        super(dataSource);

        this.rowMapper = rowMapper;
    }


    @Override
    public void setPriority(Integer priority, int eventId, Long userId) {
        updateEntity(SQL_SET_PRIORITY, priority, eventId, userId);
    }

    @Override
    public List<Participant> getPriorityForUserEvents(Long userId) {
        return findEntityList(SQL_GET_EVENT_PRIORITY, rowMapper, userId);
    }

    @Override
    public Participant getParticipant(int eventId, Long userId) {
        return findEntity(SQL_GET_PARTICIPANT, rowMapper, eventId, userId);
    }


}
