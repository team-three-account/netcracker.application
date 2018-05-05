package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/update-{name}", method = RequestMethod.GET)
    public ModelAndView updateItem(@PathVariable("name") String itemName, ModelAndView modelAndView) {
        modelAndView.addObject("updateItem", itemService.getByItemName(itemName));
        modelAndView.setViewName("item/editItem");
        return modelAndView;
    }

    @RequestMapping(value = {"/update-{name}"}, method = RequestMethod.POST)
    public ModelAndView updateItem(@ModelAttribute("updateItem") Item item,  ModelAndView modelAndView) {
        itemService.update(item);
        modelAndView.setViewName("redirect:/account/itemList");
        return modelAndView;
    }

    @RequestMapping(value = "/itemList/deleteItem-{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.delete(itemId);
        return "redirect:/account/itemList";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public ModelAndView createItem(@ModelAttribute(value = "createItem") Item item, ModelAndView modelAndView) {
//        modelAndView.addObject("newItem", itemService.getAuthenticatedUser());
          modelAndView.setViewName("item/addItem");
          return modelAndView;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public ModelAndView addItem(@ModelAttribute("createItem") Item item, ModelAndView modelAndView) {
        itemService.add(item);
        modelAndView.setViewName("redirect:/account/itemList");
        return modelAndView;
    }

    @RequestMapping(value = "/itemList", method = RequestMethod.GET)
    public String itemList(Model model) {
        model.addAttribute("itemList", itemService.itemList());
        return "item/itemList";
    }

    @RequestMapping(value = "/getItem-{itemName}", method = RequestMethod.GET)
    public String getByItemName(@PathVariable("itemName") String itemName, Item item, ModelAndView modelAndView) {
        modelAndView.addObject("item", itemService.getByItemName(itemName));
        return "item/findByName";
    }

    @RequestMapping(value = {"/personItemList-{personId}"}, method = RequestMethod.GET)
    public String findItemByPersonId(@PathVariable("personId") Long personId, Model model) {
        model.addAttribute("personItemList", itemService.findItemByPersonId(personId));
        return "item/findItemByPersonId";
    }
}
