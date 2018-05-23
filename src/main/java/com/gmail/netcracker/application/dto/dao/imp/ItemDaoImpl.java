package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Like;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ItemDaoImpl extends ModelDao implements ItemDao {

    @Value("${sql.item.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.item.add}")
    private String SQL_ADD;

    @Value("${sql.item.update}")
    private String SQL_UPDATE;

    @Value("${sql.item.delete}")
    private String DELETE_ITEM;

    @Value("${sql.item.find}")
    private String SQL_FIND_BY_ID;

    @Value("{sql.item.findItemByName}")
    private String SQL_FIND_BY_NAME;

    @Value("${sql.item.findByUserId}")
    private String SQL_FIND_BY_PERSON_ID;

    @Value("${sql.item.setRoot}")
    private String SQL_SET_ROOT;

    @Value("${sql.item.insertCopiedItem}")
    private String SQL_INSERT_COPIED_ITEM;

    @Value("${sql.item.getBookerId}")
    private String SQL_GET_BOOKER_ID;

    @Value("${sql.item.setBooker}")
    private String SQL_SET_BOOKER;

    @Value("${sql.item.unbook}")
    private String SQL_CANCEL_BOOKING;

    @Value("${sql.item.setBookerFromEvent}")
    private String SQL_SET_BOOKER_FROM_EVENT;

    @Value("${sql.item.getPopularItems}")
    private String SQL_GET_POPULAR_ITEMS;

    @Value("${sql.item.searchItems}")
    private String SQL_SEARCH_ITEMS;

    @Value("${sql.item.cancelItemsBookingFromEvent}")
    private String SQL_CANCEL_ITEMS_BOOKING_FROM_EVENT;

    @Value("${sql.tag.getItemsByTag}")
    private String SQL_GET_ITEMS_BY_TAG;

    @Value("${sql.item.like}")
    private String SQL_LIKE;

    @Value("${sql.item.countLikes}")
    private String SQL_COUNT_LIKES;

    @Value("${sql.item.isLiked}")
    private String SQL_IS_LIKED;

    @Value("${sql.item.dislike}")
    private String SQL_DISLIKE;

    private final RowMapper<Item> itemRowMapper;
    private final RowMapper<Like> likeRowMapper;

    @Autowired
    public ItemDaoImpl(DataSource dataSource, RowMapper<Item> itemRowMapper, RowMapper<Like> likeRowMapper) {
        super(dataSource);
        this.itemRowMapper = itemRowMapper;
        this.likeRowMapper = likeRowMapper;
    }

    @Override
    public Long add(Item item) {
        return insertEntity(SQL_ADD, PK_COLUMN_NAME, item.getPersonId(), item.getName(), item.getDescription(), item.getLink(), Utilities.parseStringToTimestamp(item.getDueDate()), item.getPriority(), item.getRoot(), item.getImage());
    }

    public void setRoot(Long itemId) {
        updateEntity(SQL_SET_ROOT, itemId, itemId);
    }

    @Override
    public Long insertCopiedItem(Item item, Long userId) {
        return insertEntity(SQL_INSERT_COPIED_ITEM, PK_COLUMN_NAME, userId, item.getName(), item.getDescription(), item.getLink(), item.getRoot(), item.getImage());
    }

    @Override
    public void setBooker(Long itemId, Long bookedId) {
        updateEntity(SQL_SET_BOOKER, bookedId, itemId);
    }

    @Override
    public Long getBookerId(Long itemId) {
        return findEntity(SQL_GET_BOOKER_ID, itemRowMapper, itemId).getBooker();
    }

    @Override
    public void cancelBooking(Long itemId, Long bookerId) {
        updateEntity(SQL_CANCEL_BOOKING, itemId, bookerId);
    }

    @Override
    public void setBookerFromEvent(Long itemId, Long bookerId, Long eventId) {
        updateEntity(SQL_SET_BOOKER_FROM_EVENT, bookerId, eventId, itemId);
    }

    @Override
    public List<Item> getPopularItems(int amountOfItems) {
        return findEntityList(SQL_GET_POPULAR_ITEMS, itemRowMapper, amountOfItems);
    }

    @Override
    public List<Item> search(String phrase, Long userId) {
        return findEntityList(SQL_SEARCH_ITEMS, itemRowMapper, phrase, userId);
    }

    @Override
    public void update(Item item) {
        updateEntity(SQL_UPDATE, item.getPersonId(), item.getName(), item.getDescription(), item.getLink(), Utilities.parseStringToTimestamp(item.getDueDate()), item.getPriority(), item.getImage(), item.getItemId());
    }

    @Override
    public void delete(Long itemId) {
        deleteEntity(DELETE_ITEM, itemId);
    }


    @Override
    public Item getItem(Long itemId) {
        return findEntity(SQL_FIND_BY_ID, itemRowMapper, itemId);
    }

    @Override
    public List<Item> findItemsByUserId(Long userId) {
        return findEntityList(SQL_FIND_BY_PERSON_ID, itemRowMapper, userId);
    }

    @Override
    public void cancelItemsBookingFromEvent(Long eventId) {
        updateEntity(SQL_CANCEL_ITEMS_BOOKING_FROM_EVENT, eventId);
    }

    @Override
    public List<Item> getItemsByTag(Long tagId) {
        return findEntityList(SQL_GET_ITEMS_BY_TAG, itemRowMapper, tagId);
    }

    @Override
    public void like(Long itemId, Long userId) {
        updateEntity(SQL_LIKE, itemId, userId);
    }

    @Override
    public int getLikesCount(int itemId) {
        return countRows(SQL_COUNT_LIKES, itemId);
    }

    @Override
    public Like isLiked(Long itemId, Long userId) {
        return findEntity(SQL_IS_LIKED, likeRowMapper, itemId, userId);
    }

    @Override
    public void dislike(Long itemId, Long userId) {
        deleteEntity(SQL_DISLIKE, itemId, userId);
    }
}

