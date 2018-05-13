package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.PriorityDao;
import com.gmail.netcracker.application.dto.model.EventType;
import com.gmail.netcracker.application.dto.model.Participant;
import com.gmail.netcracker.application.dto.model.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PriorityDaoImpl extends ModelDao implements PriorityDao {
    @Value("${sql.priority.getAllPriority}")
    private String SQL_GET_ALL_PRIORITY;

    @Value("${sql.participant.setPriority}")
    private String SQL_SET_PRIORITY;

    @Value("${sql.participant.getParticipation}")
    private String SQL_GET_PARTICIPANT;

    @Autowired
    private RowMapper<Participant> participantRowMapper;

    private RowMapper<Priority> rowMapper;

    @Autowired
    protected PriorityDaoImpl(DataSource dataSource, RowMapper<Priority> rowMapper) {
        super(dataSource);
        this.rowMapper=rowMapper;
    }

    @Override
    public List<Priority> getAllPriority() {
        return findEntityList(SQL_GET_ALL_PRIORITY, rowMapper);
    }

    @Override
    public void setPriorityToEvent(Integer priority, int eventId, Long userId) {
        updateEntity(SQL_SET_PRIORITY, priority, eventId, userId);
    }

    @Override
    public Participant getParticipant(int eventId, Long personId) {
        return findEntity(SQL_GET_PARTICIPANT, participantRowMapper, eventId, personId);
    }
}
