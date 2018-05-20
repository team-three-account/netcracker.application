package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Item;

import java.util.List;

public interface ItemDao {

    void update(Item item);

    void delete(Long itemId);

    Long add(Item item);

    Item getItem(Long itemId);

    List<Item> findItemsByUserId(Long userId);

    void setRoot (Long itemId);

    Long insertCopiedItem(Item item, Long userId);

    void setBooker(Long itemId, Long bookerId);

    Long getBookerId(Long itemId);

    void cancelBooking(Long itemId, Long id);

    void setBookerFromEvent(Long itemId, Long bookerId, Long eventId);

    List<Item> getPopularItems(int amountOfItems);

    List<Item> search(String query, Long userId);

    void cancelItemsBookingFromEvent(Long eventId);
}
