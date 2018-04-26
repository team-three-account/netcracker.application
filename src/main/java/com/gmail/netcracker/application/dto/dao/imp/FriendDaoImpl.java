package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FriendDaoImpl extends ModelDao implements FriendDao {
    private final String SQL_FIND_FRIENDS = "friend/findFriends.sql";
    private final String SQL_FIND_BY_NAME_OR_SURNAME = "friend/findByNameOrSurname.sql";
    private final String SQL_FIND_BY_NAME_AND_SURNAME = "friend/findByNameAndSurname.sql";
    private final String SQL_FIND_BY_ID = "friend/findById.sql";
    private final String SQL_ADD = "friend/add.sql";
    private final String SQL_FIND_OUTGOING_REQUESTS = "friend/findOutgoingRequests.sql";
    private final String SQL_CANCEL_REQUEST = "friend/cancelRequest.sql";
    private final String SQL_FIND_INCOMING_REQUESTS = "friend/findIncomingRequests.sql";
    private final String SQL_ACCEPT_REQUEST = "friend/acceptRequest.sql";
    private final String SQL_DELETE = "friend/delete.sql";

    private final RowMapper<User> userRowMapper;
    private final RowMapper<Friend> friendRowMapper;

    @Autowired
    public FriendDaoImpl(DataSource dataSource, ResourceLoader resourceLoader, Environment environment,
                         RowMapper<User> userRowMapper, RowMapper<Friend> friendRowMapper) {
        super(dataSource, resourceLoader, environment);
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
