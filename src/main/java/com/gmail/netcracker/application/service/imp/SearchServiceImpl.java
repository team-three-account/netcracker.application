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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<Event> searchPublicEvents(String query, User user) {
        return eventDao.searchInPublic(query, user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchFriends(String query, User user) {
        return friendDao.getFriendsByNameOrSurname(user.getId(), query.toLowerCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> searchUserEvents(String query, User user) {
        return eventDao.searchInUsersEvents(query, user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchUsers(String query, User user) {
        return friendDao.searchUsersByNameOrSurname(user.getId(), query.toLowerCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> searchItems(String query, User user) {
        return itemDao.search(query, user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> searchMyItems(String query, User user) {
        return itemDao.searchMy(query, user.getId());
    }
}
