package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventMessageDao;
import com.gmail.netcracker.application.dto.model.EventMessage;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class EventMessageDaoImp extends ModelDao implements EventMessageDao {

    @Value("${sql.message.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.message.add}")
    private String SQL_INSERT;
    @Value("${sql.select.last.message.for.chat}")
    private String SQL_GET_LAST_MESSAGE;

    @Autowired
    private RowMapper<EventMessage> eventMessageRowMapper;

    @Autowired
    public EventMessageDaoImp(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    @Transactional
    public void insertMessage(EventMessage message) {
        insertEntity(
                SQL_INSERT,
                PK_COLUMN_NAME,
                message.getText(),
                Utilities.parseStringToTimestampWithSeconds(message.getTime()),
                message.getSenderId(),
                message.getChatId());
    }

    @Override
    public EventMessage getLastMessage(Long chatId) {
        return findEntity(SQL_GET_LAST_MESSAGE,eventMessageRowMapper,chatId);
    }
}
