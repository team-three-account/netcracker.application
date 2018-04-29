package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//TODO this dao
@Repository
public class ItemDaoImpl extends ModelDao implements ItemDao {

    private final String UPDATE = "UPDATE public.\"Item\"\n" +
            "SET person_id=?, booker_name=?, item_name=?, link=?, due_date=?, priority=?, root=?\n" +
            "WHERE \"Item\".item_id=?;";

    private final String DELETE = "DELETE FROM public.\"Item\"\n" +
            "WHERE \"Item\".item_id=?";

    private final String ADD = "INSERT INTO public.\"Item\"(\n" +
            "item_id, person_id, booker_name, item_name, link, due_date, priority, root)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private final String ITEM_LIST = "SELECT * FROM public.\"Item\";";

    private final String ITEM_LIST_BY_PERSON = "SELECT * FROM public.\"Item\" WHERE public.\"Item\".person=?;";

    @Autowired
    public ItemDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void update(Item item) {
        jdbcTemplate.update(UPDATE, item.getPersonId(), item.getBookerName(), item.getItemName(), item.getLink(),
                item.getDueDate(), item.getPriority(), item.getRoot(), item.getItemId());
    }

    @Override
    public void delete(String itemId) {
        jdbcTemplate.update(DELETE, itemId);
    }

    @Override
    public void add(Item item) {
        jdbcTemplate.update(ADD, item.getItemId(), item.getPersonId(), item.getBookerName(), item.getLink(),
                item.getDueDate(), item.getPriority(), item.getRoot());
    }

    @Override
    public List<Item> itemList() {
        return jdbcTemplate.query(ITEM_LIST, new ItemDaoImpl.ItemRowMapper());
    }

    @Override
    public List<Item> findItemByPersonId(String personId) {
        return jdbcTemplate.query(ITEM_LIST_BY_PERSON, new ItemDaoImpl.ItemRowMapper(), personId);

    }

    // CustomerRowMapper class includes the method of the 'mapRow'
    // which is uses the method 'itemList' and 'findItemByPersonId'
    final class ItemRowMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Item item = new Item();
            item.setItemId(resultSet.getLong("item_id"));
            item.setPersonId(resultSet.getLong("person"));
            item.setBookerName(resultSet.getString("booker"));
            item.setItemName(resultSet.getString("name"));
            item.setDescription(resultSet.getString("description"));
            item.setLink(resultSet.getString("link"));
            item.setDueDate(resultSet.getString("due_date"));
            item.setPriority(resultSet.getInt("priority"));
            item.setRoot(resultSet.getLong("root"));
            return item;
        }
    }
}

