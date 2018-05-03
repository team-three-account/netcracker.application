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
    public void setPriority(Integer priority, int event_id, Long user_id) {
        updateEntity(SQL_SET_PRIORITY, priority, event_id, user_id);
    }

    @Override
    public List<Participant> getPriorityForUserEvents(Long user_id) {
        return findEntityList(SQL_GET_EVENT_PRIORITY, rowMapper, user_id);
    }

    @Override
    public Participant getParticipant(int event_id, Long user_id) {
        return findEntity(SQL_GET_PARTICIPANT, rowMapper, event_id, user_id);
    }


}
