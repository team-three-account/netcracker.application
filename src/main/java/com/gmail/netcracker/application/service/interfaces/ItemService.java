package com.gmail.netcracker.application.service.interfaces;


import com.gmail.netcracker.application.dto.model.Item;

import java.util.List;

public interface ItemService {

    void update(Item item);

    void delete(int itemId);

    void add(Item item);

    List<Item> itemList();

    Item getByItemName(String itemName);

    List<Item> findItemByPersonId(int personId);
}
