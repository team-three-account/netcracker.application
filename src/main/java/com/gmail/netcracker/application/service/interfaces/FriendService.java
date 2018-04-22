package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendService {
    List<User> getAllFriends(String id);
}
