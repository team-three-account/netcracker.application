package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.ItemDao;
import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemDao itemDao;
    @Autowired
    private UserService userService;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, UserService userService){
        this.itemDao = itemDao;
        this.userService = userService;
    }

    @Override
    public void update(Item item) {
        setPersonId(item);
        itemDao.update(item);
    }

    @Override
    public void delete(Long itemId) {
        itemDao.delete(itemId);
    }

    @Override
    @Transactional
    public void add(Item item) {
        setPersonId(item);
        itemDao.add(item);
        item.setRoot(item.getItemId());
        itemDao.update(item);
    }

    @Override
    public List<Item> itemList() {
        return itemDao.itemList();
    }

//    @Override
//    public Item getByItemName(String name) {
//        return itemDao.getByItemName(name);
//    }

    @Override
    public Item getItem(Long itemId) {
        return itemDao.getItem(itemId);
    }

    @Override
    public List<Item> findItemByPersonId(Long personId) {
        return itemDao.allPersonItem(personId);
    }

    @Override
    public void setPersonId(Item item) {
        item.setPersonId(userService.getAuthenticatedUser().getId());
    }
}
