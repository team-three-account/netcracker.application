package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendDao {

    List<User> friendList(String id);
    void deleteFriend(String person, String friend);
    List<User> getFriendsByNameOrSurname(String input);
    List<User> getFriendsByNameAndSurname(String name, String surname);

    Friend getFriendshipById(String person_id, String friend_id);

    void addFriend(String person_id, String friend_id);
}
