package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//This dao doesn't work correctly!!!
//TODO this dao, set numeric id, check sql
@Repository
public class ItemDaoImpl extends ModelDao implements ItemDao {
    private final String PK_COLUMN_NAME = "item_id";

    private final String SQL_ADD = "item/add.sql";
    private final String SQL_DELETE = "item/delete.sql";
    private final String SQL_FIND_LIST = "item/findList.sql";
    private final String SQL_FIND_PERSON_LIST = "item/findPersonList.sql";
    private final String SQL_UPDATE = "item/update.sql";

    private final RowMapper<Item> rowMapper;

    @Autowired
    protected ItemDaoImpl(DataSource dataSource, ResourceLoader resourceLoader, Environment environment,
                          RowMapper<Item> rowMapper) {
        super(dataSource, resourceLoader, environment);
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

