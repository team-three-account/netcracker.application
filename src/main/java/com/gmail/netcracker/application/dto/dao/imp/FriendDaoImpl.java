package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FriendDaoImpl extends ModelDao implements FriendDao {
    @Value("${sql.friend.findFriends}")
    private String SQL_FIND_FRIENDS;
    @Value("${sql.friend.findByNameOrSurname}")
    private String SQL_FIND_BY_NAME_OR_SURNAME;
    @Value("${sql.friend.findByNameAndSurname}")
    private String SQL_FIND_BY_NAME_AND_SURNAME;
    @Value("${sql.friend.findById}")
    private String SQL_FIND_BY_ID;
    @Value("${sql.friend.add}")
    private String SQL_ADD;
    @Value("${sql.friend.findOutgoingRequests}")
    private String SQL_FIND_OUTGOING_REQUESTS;
    @Value("${sql.friend.cancelRequest}")
    private String SQL_CANCEL_REQUEST;
    @Value("${sql.friend.findIncomingRequests}")
    private String SQL_FIND_INCOMING_REQUESTS;
    @Value("${sql.friend.acceptRequest}")
    private String SQL_ACCEPT_REQUEST;
    @Value("${sql.friend.delete}")
    private String SQL_DELETE;

    private final RowMapper<User> userRowMapper;
    private final RowMapper<Friend> friendRowMapper;

    @Autowired
    public FriendDaoImpl(DataSource dataSource, RowMapper<User> userRowMapper, RowMapper<Friend> friendRowMapper) {
        super(dataSource);
        this.userRowMapper = userRowMapper;
        this.friendRowMapper = friendRowMapper;
    }

    @Override
    public List<User> friendList(Long id) {
        return findEntityList(SQL_FIND_FRIENDS, userRowMapper, id, id, id);
    }

    @Override
    public void deleteFriend(Long person, Long friend) {
        deleteEntity(SQL_DELETE, person, friend);
    }

    @Override
    public List<User> getFriendsByNameOrSurname(String input) {
        return findEntityList(SQL_FIND_BY_NAME_OR_SURNAME, userRowMapper, input, input);
    }

    @Override
    public List<User> getFriendsByNameAndSurname(String name, String surname) {
        return findEntityList(SQL_FIND_BY_NAME_AND_SURNAME, userRowMapper, name, surname, name, surname);
    }

    @Override
    public Friend getFriendshipById(Long person_id, Long friend_id) {
        return findEntity(SQL_FIND_BY_ID, friendRowMapper, person_id, friend_id, person_id, friend_id);
    }

    @Override
    public void addFriend(Long person_id, Long friend_id) {
        updateEntity(SQL_ADD, person_id, friend_id);
    }

    @Override
    public List<User> getOutgoingRequests(Long id) {
        return findEntityList(SQL_FIND_OUTGOING_REQUESTS, userRowMapper, id);
    }

    @Override
    public void cancelRequest(Long id, Long friend_id) {
        updateEntity(SQL_CANCEL_REQUEST, id, friend_id);
    }

    @Override
    public List<User> getIncomingRequests(Long id) {
        return findEntityList(SQL_FIND_INCOMING_REQUESTS, userRowMapper, id);
    }

    @Override
    public void acceptRequest(Long id, Long friend_id) {
        updateEntity(SQL_ACCEPT_REQUEST, id, friend_id);
    }
}
