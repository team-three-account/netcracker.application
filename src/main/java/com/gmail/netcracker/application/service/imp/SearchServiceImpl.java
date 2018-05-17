package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.EventDao;
import com.gmail.netcracker.application.dto.dao.interfaces.FriendDao;
import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    EventDao eventDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    FriendDao friendDao;

    @Override
    public List<Event> searchPublicEvents(String query, User user) {
        return eventDao.searchInPublic(query, user.getId());
    }

    @Override
    public List<User> searchFriends(String query, User user) {
        return friendDao.getFriendsByNameOrSurname(user.getId(), query.toLowerCase());
    }

    @Override
    public List<Event> searchUserEvents(String query, User user) {
        return eventDao.searchInUsersEvents(query, user.getId());
    }

    @Override
    public List<User> searchUsers(String query, User user){
        return friendDao.searchUsersByNameOrSurname(user.getId(), query.toLowerCase());
    }

    @Override
    public List<Item> searchItems(String query, User user){
        return itemDao.search(query, user.getId());
    }
}
