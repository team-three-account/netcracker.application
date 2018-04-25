package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendDao {

    List<User> friendList(Long id);
    void deleteFriend(Long person, Long friend);
    List<User> getFriendsByNameOrSurname(String input);
    List<User> getFriendsByNameAndSurname(String name, String surname);

    Friend getFriendshipById(Long person_id, Long friend_id);

    void addFriend(Long person_id, Long friend_id);

    List<User> getOutgoingRequests(Long id);

    void cancelRequest(Long id, Long friend_id);

    List<User> getIncomingRequests(Long id);

    void acceptRequest(Long id, Long friend_id);
}
