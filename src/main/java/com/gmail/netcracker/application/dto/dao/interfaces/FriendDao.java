package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FriendDao {

    List<User> friendList(String id);
}
