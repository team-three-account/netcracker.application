package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ChatDao;
import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ChatDaoImpl extends ModelDao implements ChatDao {

    @Value("${sql.chat.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.chat.add}")
    private String SQL_INSERT;

    @Value("${sql.chat.getChat}")
    private String SQL_GET_CHAT_BY_EVENT_ID;

    @Value("${sql.chat.getChatByChatId}")
    private String SQL_GET_CHAT_BY_CHAT_ID;

    @Value("${sql.chat.delete}")
    private String SQL_DELETE;

    @Value("${sql.chat.getMessages}")
    private String SQL_GET_LIST;

    @Value("${sql.chats.getAllChatsForCurrentUser}")
    private String SQL_GET_CHATS_FOR_USER;

    @Value("${sql.chat.getAllInformationAboutCurrentUserChat}")
    private String SQL_ALL_CHAT_INFORMATION;

    @Autowired
    private EventDao eventDao;
    @Autowired
    private RowMapper<EventMessage> rowMapper;
    @Autowired
    private RowMapper<Chat> chatRowMapper;
    @Autowired
    private RowMapper<ChatId> notificationRowMapper;
    @Autowired
    private RowMapper<Notification> chatUserRowMapper;

    private Logger logger = Logger.getLogger(ChatDaoImpl.class.getName());

    @Autowired
    public ChatDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Chat getChatByEventId(Event event, Boolean state) {
        return findEntity(SQL_GET_CHAT_BY_EVENT_ID, chatRowMapper, event.getEventId(), state);
    }

    @Override
    public Chat getChatByChatId(Long chatId) {
        return findEntity(SQL_GET_CHAT_BY_CHAT_ID, chatRowMapper, chatId);
    }

    @Override
    public void createChat(Event event, Boolean creator) {
        logger.info(event.toString());
        insertEntity(SQL_INSERT, PK_COLUMN_NAME, event.getName(), creator, event.getEventId());
    }

    @Override
    public List<EventMessage> getMessages(Long eventId, Long chatId, Boolean state) {
        return findEntityList(SQL_GET_LIST, rowMapper, eventId, chatId, state);
    }

    @Override
    public List<EventMessage> getMessages(Long eventId, Long chatId, Boolean state, Integer limit, Integer offset) {
        return findEntityList(SQL_GET_LIST, rowMapper, limit, offset, eventId, chatId, state);
    }

    @Override
    public void deleteChat(Event event) {
        deleteEntity(SQL_DELETE, event.getEventId());
    }

    @Override
    public List<ChatId> allUserChatId(Long userId) {
        return findEntityList(SQL_GET_CHATS_FOR_USER, notificationRowMapper, userId);
    }

    @Override
    public List<Notification> allUserChats(Long userId) {
        return findEntityList(SQL_ALL_CHAT_INFORMATION, chatUserRowMapper, userId);
    }
}
