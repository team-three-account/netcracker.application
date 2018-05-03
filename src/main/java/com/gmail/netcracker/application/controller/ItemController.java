package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/update-{name}", method = RequestMethod.GET)
    public String updateItemPage(@PathVariable("name") String itemName, Model model) {
        model.addAttribute("item", itemService.getByItemName(itemName));
        return "item/editItem";
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") Item item){
        itemService.update(item);
        return "redirect:/account/itemList";
    }
    @RequestMapping(value = "/itemList/deleteItem-{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.delete(itemId);
        return "redirect:/account/itemList";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String createItemPage() {
        return "item/addItem";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public String addItem(@ModelAttribute("item") Item item) {
        itemService.add(item);
        return "redirect:/account/itemList";
    }

    @RequestMapping(value = "/itemList", method = RequestMethod.GET)
    public String itemList(Model model) {
        model.addAttribute("itemList", itemService.itemList());
        return "item/itemList";
    }

    @RequestMapping(value = "/getItem-{itemName}", method = RequestMethod.GET)
    public String getByItemName(@PathVariable("itemName") String itemName, Model model){
        model.addAttribute("item", itemService.getByItemName(itemName));
        return "item/findByName";
    }

    @RequestMapping(value = {"/personItemList-{personId}"}, method = RequestMethod.GET)
    public String findItemByPersonId(@PathVariable("personId") Long personId, Model model) {
        model.addAttribute("personItemList", itemService.findItemByPersonId(personId));
        return "item/findItemByPersonId";
    }
}
