package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemServiceImpl implements ItemService {


    @Autowired
    public ItemDao itemDao;

    @Override
    public void update(Item item) {
        itemDao.update(item);
    }

    @Override
    public void delete(int itemId) {
        itemDao.delete(itemId);
    }

    @Override
    public void add(Item item) {
        itemDao.add(item);
    }

    @Override
    public List<Item> itemList() {
        return itemDao.itemList();
    }

    @Override
    public Item getByItemName(String itemName) {
        return null;
    }

    @Override
    public List<Item> findItemByPersonId(int personId) {
        return itemDao.allPersonItem(personId);
    }
}
