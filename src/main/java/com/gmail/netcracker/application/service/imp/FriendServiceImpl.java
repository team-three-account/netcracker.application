package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDao friendDao;

    @Override
    public List<User> getAllFriends(String id) {
        return friendDao.friendList(id);
    }}
