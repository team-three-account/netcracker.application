package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    public ItemDao itemDao;

    @Override
    public void update(Item item) {
        itemDao.update(item);
    }

    @Override
    public void delete(String itemId) {
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
    public List<Item> findItemByPersonId(String personId) {
        return itemDao.findItemByPersonId(personId);
    }
}
