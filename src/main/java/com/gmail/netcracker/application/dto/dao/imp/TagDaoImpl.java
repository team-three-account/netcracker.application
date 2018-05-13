package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.TagDao;
import com.gmail.netcracker.application.dto.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TagDaoImpl extends ModelDao implements TagDao {

    @Value("tag_id")
    String PK_COLUMN_NAME;

    @Value("${sql.tag.getAll}")
    String SQL_GET_ALL_TAG;

    @Value("${sql.tag.getTagsOfItem}")
    String SQL_GET_TAGS_OF_ITEM;

    @Value("${sql.tag.addTag}")
    String SQL_ADD_TAG;

    @Value("${sql.tag.addTagToItem}")
    String SQL_ADD_TAG_TO_ITEM;

    @Value("${sql.tag.findByName}")
    String SQL_FIND_TAG_BY_NAME;

    @Value("${sql.tag.deleteTagOfItem}")
    String SQL_DELETE_TAG_OF_ITEM;

    RowMapper<Tag> tagRowMapper;

    @Autowired
    protected TagDaoImpl(DataSource dataSource, RowMapper<Tag> tagRowMapper) {
        super(dataSource);
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Set<Tag> getAllTag() {
        return findEntitySet(SQL_GET_ALL_TAG, tagRowMapper);
    }

    @Override
    public Set<Tag> getTagsOfItem(Long itemId) {
        return new HashSet<>(findEntityList(SQL_GET_TAGS_OF_ITEM, tagRowMapper, itemId));
    }

    @Override
    public Long addTag(String tag) {
        return insertEntity(SQL_ADD_TAG, PK_COLUMN_NAME, tag);
    }

    @Override
    public void addTagToItem(Long tagId, Long itemId) {
        insertEntity(SQL_ADD_TAG_TO_ITEM, PK_COLUMN_NAME, tagId, itemId);
    }

    @Override
    public Tag findTagByName(String name) {
        return findEntity(SQL_FIND_TAG_BY_NAME, tagRowMapper, name);
    }

    @Override
    public void deleteTagOfItem(Long tagId, Long itemId) {
        deleteEntity(SQL_DELETE_TAG_OF_ITEM, tagId, itemId);
    }

}
