package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.PriorityDao;
import com.gmail.netcracker.application.dto.model.EventType;
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
}
