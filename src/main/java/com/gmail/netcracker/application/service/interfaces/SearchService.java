package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface SearchService {

    List<Event> searchPublicEvents(String query, User user);

    List<User> searchFriends(String query, User user);

    List<Event> searchUserEvents(String query, User user);

    List<User> searchUsers(String query, User user);

    List<Item> searchItems(String query, User user);

    List<Item> searchMyItems(String query, User user);
}
