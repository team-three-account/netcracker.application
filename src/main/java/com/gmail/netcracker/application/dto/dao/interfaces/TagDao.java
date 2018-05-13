package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagDao {

    Set<Tag> getAllTag();

    Set<Tag> getTagsOfItem(Long itemId);

    Long addTag(String tag);

    void addTagToItem(Long tagId, Long itemId);

    Tag findTagByName(String name);

    void deleteTagOfItem(Long tagId, Long itemId);
}
