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

    @RequestMapping(value = {"/account/itemsList/deleteItem-{itemId}"}, method = RequestMethod.GET)
    public String deleteItem(@PathVariable String itemId){
        itemService.delete(itemId);
        return "redirect:/account/itemsList";
    }

    @RequestMapping(value = "/account/itemsList/addItem", method = RequestMethod.GET)
    private String createItemPage(){
        return "addItem";
    }

    @RequestMapping(value = "/account/itemsList/addItem", method = RequestMethod.POST)
        public String addItem(@ModelAttribute("item") Item item){
        itemService.add(item);
        return "redirect:/itemsList";
    }
    @RequestMapping(value = "/account/itemsList", method = RequestMethod.GET)
    public String itemList (Model model) {
        model.addAttribute("itemsList", itemService.itemList());
        return "item/itemsList";
    }
    @RequestMapping(value = {"account/itemsList/findItemByPersonId-{personId}"}, method = RequestMethod.GET)
    public String findItemByPersonId(@PathVariable("personId") String personId, Model model){
         model.addAttribute("itemsList", itemService.findItemByPersonId(personId));
         return "redirect:/item/findItemByPersonId";
    }
}
