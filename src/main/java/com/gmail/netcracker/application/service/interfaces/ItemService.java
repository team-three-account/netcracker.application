package com.gmail.netcracker.application.service.interfaces;


import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.dto.model.Tag;

import java.util.List;
import java.util.Set;

public interface ItemService {

    void update(Item item);

    void delete(Long itemId);

    void add(Item item);

    Item getItem(Long itemId);

    List<Item> getWishList(Long personId);

    void setPersonId(Item item);

    List<Priority> getAllPriorities();

    void copyItem(Long itemId);

    void bookItem(Long itemId);

    void cancelBookingItem(Long itemId);

    void bookItemFromEvent(Long itemId, Long eventId);

    Set<String> parseTags(String tags);

    void addTagsToItem(Set<String> tags, Long itemId);

    Set<Tag> getTagsOfItem(Long itemId);

    void addTagsToCopiedItem(Set<Tag> tags, Long itemId);

    void addTagsToNewItem(Set<String> tags, Long itemId);

    List<Item> popularItems();

    void like(Long itemId, Long userId);

    Long countLikes(Long itemId);

    Boolean isLiked(Long itemId, Long userId);

    void dislike(Long itemId, Long userId);

    List<Tag> popularTags();

    List<Item> getItemsByTag(Long tag);

    Tag getTagByName(String tagName);
}
