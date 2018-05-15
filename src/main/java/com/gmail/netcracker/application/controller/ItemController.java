package com.gmail.netcracker.application.controller;

import com.dropbox.core.DbxException;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/account")
public class ItemController {

    private final UserService userService;
    private final ItemService itemService;
    private final ItemValidator itemValidator;
    private PhotoServiceImp photoService;

    @Autowired
    public ItemController(ItemService itemService, UserService userService, ItemValidator itemValidator, PhotoServiceImp photoService) {
        this.itemService = itemService;
        this.userService = userService;
        this.itemValidator = itemValidator;
        this.photoService = photoService;
    }

    @RequestMapping(value = "/update-{itemId}", method = RequestMethod.GET)
    public ModelAndView updateItem(@PathVariable("itemId") Long itemId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("updateItem", itemService.getItem(itemId));
        modelAndView.setViewName("item/editItem");
        return modelAndView;
    }

    @RequestMapping(value = {"/update-{itemId}"}, method = RequestMethod.POST)
    public ModelAndView updateItem(@ModelAttribute("updateItem") Item item,
                                   @RequestParam(value = "photoInput") String image,
                                   @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("item/editItem");
        item.setImage(image);
        itemValidator.validate(item, bindingResult);
        if (bindingResult.hasErrors() || !multipartFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !multipartFile.getContentType().equals(photoService.getImageTypeJpg())
                && !multipartFile.getContentType().equals(photoService.getImageTypePng())
                && !multipartFile.isEmpty()) {
            modelAndView.addObject("message", "Image type don't supported");
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            item.setImage(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }
        itemService.update(item);
        modelAndView.setViewName("redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList");
        return modelAndView;
    }

    @RequestMapping(value = "/wishList/deleteItem-{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.delete(itemId);
        return "redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public ModelAndView createItem(@ModelAttribute(value = "createItem") Item item, ModelAndView modelAndView) {
        item.setImage(photoService.getDefaultImageForItems());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("item/addItem");
        return modelAndView;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public ModelAndView addItem(@ModelAttribute("createItem") Item item,
                                @RequestParam(value = "photoInput") String image,
                                @RequestParam(value = "photoFile") MultipartFile multipartFile,
                                BindingResult bindingResult,
                                ModelAndView modelAndView) throws IOException, DbxException {
        modelAndView.setViewName("item/addItem");
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        item.setImage(image);
        itemValidator.validate(item, bindingResult);
        if (bindingResult.hasErrors() || !multipartFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !multipartFile.getContentType().equals(photoService.getImageTypeJpg())
                && !multipartFile.getContentType().equals(photoService.getImageTypePng())
                && !multipartFile.isEmpty()) {
            modelAndView.addObject("message", "Image type don't supported");
            return modelAndView;
        }
        if (!multipartFile.isEmpty()) {
            item.setImage(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
        }
        itemService.add(item);
        modelAndView.setViewName("redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList");
        return modelAndView;
    }

    @RequestMapping(value = "/getItem-{itemId}", method = RequestMethod.GET)
    public ModelAndView getItem(@PathVariable("itemId") Long itemId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("getItem", itemService.getItem(itemId));
        modelAndView.setViewName("item/getItem");
        return modelAndView;
    }

    @RequestMapping(value = "/copy-{itemId}", method = RequestMethod.GET)
    public String copyItem(@PathVariable("itemId") Long itemId) {
        itemService.copyItem(itemId);
        return "redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList";
    }

    @ModelAttribute("priorities")
    public List<Priority> getAllPriorities() {
        return itemService.getAllPriorities();
    }

    @RequestMapping(value = "/user-{ownerId}/item-{itemId}/book", method = RequestMethod.GET)
    public String bookItem(@PathVariable("itemId") Long itemId, @PathVariable("ownerId") Long ownerId) {
        itemService.bookItem(itemId);
        return "redirect:/account/user-" + ownerId + "/wishList";
    }

    @RequestMapping(value = "/user-{id}/item-{itemId}/cancel-booking", method = RequestMethod.GET)
    public String cancelBookingItem(@PathVariable("itemId") Long itemId, @PathVariable("id") Long owner) {
        itemService.cancelBookingItem(itemId);
        return "redirect:/account/user-" + owner + "/wishList";
    }

    @RequestMapping(value = "/user-{id}/wishList", method = RequestMethod.GET)
    public String userWishList(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("ownerId", userId);
        model.addAttribute("wishList", itemService.getWishList(userId));
        model.addAttribute("popularItems", itemService.popularItems());
        return "item/personWishList";
    }

    @RequestMapping(value = "/event-{eventId}-{creator}/wishList", method = RequestMethod.GET)
    public String eventWishList(@PathVariable("eventId") Long eventId, @PathVariable("creator") Long creator, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("eventId", eventId);
        model.addAttribute("ownerId", creator);
        model.addAttribute("wishList", itemService.getWishList(creator));
        model.addAttribute("popularItems", itemService.popularItems());
        return "item/eventWishList";
    }

    @RequestMapping(value = "/event-{eventId}-{creator}/item-{itemId}/book", method = RequestMethod.GET)
    public String bookFromEvent(@PathVariable("itemId") Long itemId, @PathVariable("eventId") Long eventId, @PathVariable("creator") Long creator) {
        itemService.bookItemFromEvent(itemId, eventId);
        return "redirect:/account/event-" + eventId + "-" + creator + "/wishList";
    }

    @RequestMapping(value = "/tags-{itemId}", method = RequestMethod.GET)
    public String tagsView(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("tags", itemService.getTagsOfItem(itemId));
        return "item/tags";
    }

    @RequestMapping(value = "/tagsEdit-{itemId}", method = RequestMethod.GET)
    public String tagsEdit(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "item/tagsEdit";
    }

    @RequestMapping(value = "/tagsEdit-{itemId}", method = RequestMethod.POST)
    public String tagsSave(@PathVariable("itemId") Long itemId,
                           @RequestParam("tags") String tags, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        itemService.addTagsToItem(itemService.parseTags(tags), itemId);
        return "redirect:/account/tags-" + itemId;
    }
}