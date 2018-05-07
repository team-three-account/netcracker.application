package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Item;

import java.util.List;

public interface ItemDao {

    void update(Item item);

    void delete(Long itemId);

    void add(Item item);

    List<Item> wishList();

    Item getItem(Long itemId);

    List<Item> allPersonItem(Long personId);
}
