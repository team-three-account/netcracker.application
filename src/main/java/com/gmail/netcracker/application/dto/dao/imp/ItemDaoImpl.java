package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//This dao doesn't work correctly!!!
//TODO this dao, set numeric id, check sql
@Repository
public class ItemDaoImpl extends ModelDao implements ItemDao {
    @Value("${sql.item.pkColumnName}")
    private String PK_COLUMN_NAME;

    @Value("${sql.item.add}")
    private String SQL_ADD;
    @Value("${sql.item.delete}")
    private String SQL_DELETE;
    @Value("${sql.item.findList}")
    private String SQL_FIND_LIST;
    @Value("${sql.item.findPersonList}")
    private String SQL_FIND_PERSON_LIST;
    @Value("${sql.item.update}")
    private String SQL_UPDATE;

    private final RowMapper<Item> rowMapper;

    @Autowired
    protected ItemDaoImpl(DataSource dataSource, RowMapper<Item> rowMapper) {
        super(dataSource);
        this.rowMapper = rowMapper;
    }

    @Override
    public void update(Item item) {
        updateEntity(SQL_UPDATE,
                item.getPersonId(),
                item.getBookerName(),
                item.getItemName(),
                item.getLink(),
                item.getDueDate(),
                item.getPriority(),
                item.getRoot(),
                item.getItemId());
    }

    @Override
    public void delete(String itemId) {
        deleteEntity(SQL_DELETE, itemId);
    }

    @Override
    public void add(Item item) {
        insertEntity(SQL_ADD, PK_COLUMN_NAME,
                item.getPersonId(),
                item.getBookerName(),
                item.getLink(),
                item.getDueDate(),
                item.getPriority(),
                item.getRoot());
    }

    @Override
    public List<Item> itemList() {
        return findEntityList(SQL_FIND_LIST, rowMapper);
    }

    @Override
    public List<Item> findItemByPersonId(String personId) {
        return findEntityList(SQL_FIND_PERSON_LIST, rowMapper, personId);
    }
}

