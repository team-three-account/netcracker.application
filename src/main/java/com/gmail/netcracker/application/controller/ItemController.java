package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

        @RequestMapping(value = "/update/{itemName}", method = RequestMethod.GET)
    public String updateItemPage(@PathVariable("itemName") String itemName, Model model) {
       model.addAttribute("update", itemService.getByItemName(itemName));
        return "item/editItem";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") Item item){
        itemService.update(item);
        return "redirect:/account/itemList";
    }
    @RequestMapping(value = "/deleteItem/{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") int itemId) {
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

    @RequestMapping(value = "/account/itemList", method = RequestMethod.GET)
    public String itemList(Model model) {
        model.addAttribute("itemList", itemService.itemList());
        return "item/itemList";
    }

    @RequestMapping(value = "/item/{itemName}", method = RequestMethod.GET)
    public String getByItemName(@PathVariable("itemName") String itemName, Model model){
        model.addAttribute("item", itemService.getByItemName(itemName));
        return "";
    }

    @RequestMapping(value = {"/personItemList/{personId}"}, method = RequestMethod.GET)
    public String findItemByPersonId(@PathVariable("personId") int personId, Model model) {
        model.addAttribute("personItemList", itemService.findItemByPersonId(personId));
        return "item/findItemByPersonId";
    }
}
