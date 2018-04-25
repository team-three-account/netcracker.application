package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.model.Friend;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    Friend friendship;

    @Override
    public List<User> getAllFriends(String id) {
        return friendDao.friendList(id);
    }

    @Override
    public List<User> searchFriends(String search) {
        String [] input = search.split(" ");
        return input.length > 1 ? friendDao.getFriendsByNameAndSurname(input[0].toLowerCase(), input[1].toLowerCase()) : friendDao.getFriendsByNameOrSurname(input[0].toLowerCase());
    }


    @Override
    public void addFriend(String person_id, String friend_id) {
        friendship = friendDao.getFriendshipById(person_id, friend_id);
        if(friendship.getSender() == null && friendship.getRecipient()== null) {
            friendDao.addFriend(person_id, friend_id);
        }
    }

    @Override
    public List<User> getOutgoingRequests(String id) {
        return friendDao.getOutgoingRequests(id);
    }

    @Override
    public void cancelRequest(String id, String friend_id) {
        friendDao.cancelRequest(id, friend_id);
    }

    @Override
    public List<User> getIncomingRequests(String id) {
        return friendDao.getIncomingRequests(id);
    }

    @Override
    public void acceptRequest(String id, String friend_id) {
        friendDao.acceptRequest(id, friend_id);
    }
}
