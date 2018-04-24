package com.gmail.netcracker.application.service.interfaces;


import com.gmail.netcracker.application.dto.model.Item;

import java.util.List;

public interface ItemService {
    void update(Item item);

    void delete(String itemId);

    void add(Item item);

    List<Item> itemList();

    List<Item> findItemByPersonId(String personId);
}
