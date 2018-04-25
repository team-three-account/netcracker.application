package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendService {
    List<User> getAllFriends(Long id);

    List<User> searchFriends(Long id, String search);

    void addFriend(Long person_id, Long friend_id);

    List<User> getOutgoingRequests(Long id);

    void cancelRequest(Long id, Long friend_id);

    List<User> getIncomingRequests(Long id);

    void acceptRequest(Long id, Long friend_id);

    void deleteFriend(Long id, Long friend_id);

    List<User> searchUsers(Long id, String search);
}
