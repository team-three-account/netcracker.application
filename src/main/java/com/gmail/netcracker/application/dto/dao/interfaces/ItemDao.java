package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Item;

import java.util.List;

public interface ItemDao {

    void update(Item item);

    void delete(int itemId);

    void add(Item item);

    List<Item> itemList();

    Item getByItemName(String itemName);

    List<Item> allPersonItem(int personId);
}
