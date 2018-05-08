package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Item;

import java.sql.Date;
import java.util.List;

public interface ItemDao {

    void update(Item item);

    void delete(Long itemId);

    Long add(Item item);

    Item getItem(Long itemId);

    List<Item> findItemsByPersonId(Long personId);

    void setRoot (Long itemId);

}
