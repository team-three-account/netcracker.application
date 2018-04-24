package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendService {
    List<User> getAllFriends(String id);

    List<User> searchFriends(String search);

    void addFriend(String person_id, String friend_id);

    List<User> getOutgoingRequests(String id);

    void cancelRequest(String id, String friend_id);
}
