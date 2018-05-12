package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendService {
    List<User> getAllFriends(Long id);

    List<User> searchFriends(Long id, String search);

    void addFriend(Long personId, Long friendId);

    List<User> getOutgoingRequests(Long id);

    void cancelRequest(Long id, Long friendId);

    List<User> getIncomingRequests(Long id);

    void acceptRequest(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);

    List<User> searchUsers(Long id, String search);

    List<User> subtractionFromFriendList(List<User> foundUsers);

    Friend getFriendshipById(Long personId, Long friendId);
}
