package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendDao {

    List<User> friendList(Long id);
    void deleteFriend(Long person, Long friend);
    List<User> getFriendsByNameOrSurname(Long id, String input);
    List<User> getFriendsByNameAndSurname(Long id, String name, String surname);

    Friend getFriendshipById(Long personId, Long friendId);

    void addFriend(Long personId, Long friendId);

    List<User> getOutgoingRequests(Long id);

    void cancelRequest(Long id, Long friendId);

    List<User> getIncomingRequests(Long id);

    void acceptRequest(Long id, Long friendId);

    List<User> searchUsersByNameAndSurname(Long id, String name, String surname);
    List<User> searchUsersByNameOrSurname(Long id, String search);
}
