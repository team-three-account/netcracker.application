package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventTypeDao;
import com.gmail.netcracker.application.dto.model.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventTypeDaoImpl extends ModelDao implements EventTypeDao{
    @Value("${sql.eventType.getAllEventTypes}")
    private String SQL_GET_ALL_EVENT_TYPES;

    private RowMapper<EventType> rowMapper;

    @Autowired
    protected EventTypeDaoImpl(DataSource dataSource, RowMapper<EventType> rowMapper) {
        super(dataSource);
        this.rowMapper=rowMapper;
    }

    @Override
    public List<EventType> getAllEventTypes() {

        return findEntityList(SQL_GET_ALL_EVENT_TYPES, rowMapper);
    }
}
