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

    @Value("${sql.friend.findUserByNameOrSurname}")
    private String SQL_FIND_USER_BY_NAME_OR_SURNAME; //TODO use LIKE

    @Value("${sql.friend.findUserByNameAndSurname}")
    private String SQL_FIND_USER_BY_NAME_AND_SURNAME;  //TODO use LIKE

    @Value("${sql.friend.findFriendshipById}")
    private String SQL_FIND_FRIENDSHIP_BY_ID;

    @Value("${sql.friend.addRequestedUser}")
    private String SQL_ADD_REQUESTED_USER;

    @Value("${sql.friend.findOutgoingRequests}")
    private String SQL_FIND_OUTGOING_REQUESTS;

    @Value("${sql.friend.cancelRequest}")
    private String SQL_CANCEL_REQUEST;

    @Value("${sql.friend.findIncomingRequests}")
    private String SQL_FIND_INCOMING_REQUESTS;

    @Value("${sql.friend.acceptRequest}")
    private String SQL_ACCEPT_REQUEST;

    @Value("${sql.friend.deleteFriend}")
    private String SQL_DELETE;

    @Value("${sql.friend.findFriendByNameOrSurname}")
    private String SQL_FIND_FRIEND_BY_NAME_OR_SURNAME;

    @Value("${sql.friend.findFriendByNameAndSurname}")
    private String SQL_FIND_FRIEND_BY_NAME_AND_SURNAME;

    private final RowMapper<User> userRowMapper;
    private final RowMapper<Friend> friendshipRowMapper;

    @Autowired
    public FriendDaoImpl(DataSource dataSource, RowMapper<Friend> friendshipRowMapper, RowMapper<User> userRowMapper) {
        super(dataSource);
        this.friendshipRowMapper = friendshipRowMapper;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> friendList(Long id) {
        return findEntityList(SQL_FIND_FRIENDS, userRowMapper, id, id, id);
    }

    @Override
    public void deleteFriend(Long person, Long friend) {
        deleteEntity(SQL_DELETE, person, friend, person, friend);
    }

    @Override
    public List<User> getFriendsByNameOrSurname(Long id, String input) {
        return findEntityList(SQL_FIND_FRIEND_BY_NAME_OR_SURNAME, userRowMapper,
                id, id, id, input, input, input, input);
    }

    @Override
    public List<User> getFriendsByNameAndSurname(Long id, String name, String surname) {
        return findEntityList(SQL_FIND_FRIEND_BY_NAME_AND_SURNAME, userRowMapper,
                id, id, id, name, surname, name, surname);
    }

    @Override
    public Friend getFriendshipById(Long personId, Long friendId) {
        return findEntity(SQL_FIND_FRIENDSHIP_BY_ID, friendshipRowMapper,
                personId, friendId, personId, friendId);
    }

    @Override
    public void addFriend(Long personId, Long friendId) {
        updateEntity(SQL_ADD_REQUESTED_USER, personId, friendId);
    }

    @Override
    public List<User> getOutgoingRequests(Long id) {
        return findEntityList(SQL_FIND_OUTGOING_REQUESTS, userRowMapper, id);
    }

    @Override
    public void cancelRequest(Long id, Long friendId) {
        updateEntity(SQL_CANCEL_REQUEST, id, friendId, id, friendId);
    }

    @Override
    public List<User> getIncomingRequests(Long id) {
        return findEntityList(SQL_FIND_INCOMING_REQUESTS, userRowMapper, id);
    }

    @Override
    public void acceptRequest(Long id, Long friendId) {
        updateEntity(SQL_ACCEPT_REQUEST, id, friendId, id, friendId);
    }

    @Override
    public List<User> searchUsersByNameAndSurname(Long id, String name, String surname) {
        return findEntityList(SQL_FIND_USER_BY_NAME_AND_SURNAME, userRowMapper,
                name, surname, name, surname, id);
    }

    @Override
    public List<User> searchUsersByNameOrSurname(Long id, String search) {
        return findEntityList(SQL_FIND_USER_BY_NAME_OR_SURNAME, userRowMapper,
                search,search,id);
    }
}
