package com.gmail.netcracker.application.controller;

import com.dropbox.core.DbxException;
import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.ImageValidator;
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
import java.util.logging.Logger;

/**
 * Class {@code ItemController} connects business logic and web view through url patterns.
 *
 * @author Vladimir Shuliak
 */

@Controller
@RequestMapping("/account")
public class ItemController {

  @Autowired
  private UserService userService;
  @Autowired
  private ItemService itemService;
  @Autowired
  private ItemValidator itemValidator;
  @Autowired
  private PhotoServiceImp photoService;
  @Autowired
  private ImageValidator imageValidator;

  @Autowired
  public ItemController(ItemService itemService, UserService userService, ItemValidator itemValidator, PhotoServiceImp photoService, ImageValidator imageValidator) {
    this.itemService = itemService;
    this.userService = userService;
    this.itemValidator = itemValidator;
    this.photoService = photoService;
    this.imageValidator = imageValidator;
  }

  /**
   * Method returns web page for update item.
   *
   * @param itemId
   * @param modelAndView
   * @return modelAndView
   */
  @RequestMapping(value = "/update-{itemId}", method = RequestMethod.GET)
  public ModelAndView updateItem(@PathVariable("itemId") Long itemId, ModelAndView modelAndView) {
    modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
    modelAndView.addObject("updateItem", itemService.getItem(itemId));
    modelAndView.setViewName("item/editItem");
    return modelAndView;
  }

  /**
   * Method checks the fields for correctness.
   * If the validation was successful, the method changes the data.
   * Otherwise, the method return error message.
   *
   * @param item
   * @param image
   * @param multipartFile
   * @param bindingResult
   * @param modelAndView
   * @return modelAndView
   */
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
    Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
    if (bindingResult.hasErrors() || imageFormat.equals(false)) {
      return modelAndView;
    }
    if (!multipartFile.isEmpty()) {
      if (!item.getImage().equals(photoService.getDefaultImageForItems())) {
        photoService.deleteFile(item.getImage());
      }
      item.setImage(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
    }
    itemService.update(item);
    modelAndView.setViewName("redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList");
    return modelAndView;
  }

  /**
   * Method removes the item from Database.
   *
   * @param itemId
   * @return view
   */
  @RequestMapping(value = "/wishList/deleteItem-{itemId}", method = RequestMethod.GET)
  public String deleteItem(@PathVariable("itemId") Long itemId) {
    itemService.delete(itemId);
    return "redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList";
  }

  /**
   * Method returns web page for create a new item.
   *
   * @param item
   * @param modelAndView
   * @return modelAndView
   */
  @RequestMapping(value = "/addItem", method = RequestMethod.GET)
  public ModelAndView createItem(@ModelAttribute(value = "createItem") Item item, ModelAndView modelAndView) {
    item.setImage(photoService.getDefaultImageForItems());
    modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
    modelAndView.setViewName("item/addItem");
    return modelAndView;
  }

  /**
   * Method method checks the fields for correctness.
   * If the validation was successful, the method changes the data.
   * Otherwise, the method returns a missed message.
   *
   * @param item
   * @param image
   * @param multipartFile
   * @param bindingResult
   * @param modelAndView
   * @return modelAndView
   */
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
    Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, multipartFile);
    if (bindingResult.hasErrors() || imageFormat.equals(false)) {
      return modelAndView;
    }
    if (!multipartFile.isEmpty()) {
      item.setImage(photoService.uploadFileOnDropBox(multipartFile, UUID.randomUUID().toString()));
    }
    itemService.add(item);
    modelAndView.setViewName("redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList");
    return modelAndView;
  }

  /**
   * Method copies the item of other users to own Wish List.
   *
   * @param itemId
   * @return view
   */
  @RequestMapping(value = "/copy-{itemId}", method = RequestMethod.GET)
  public String copyItem(@PathVariable("itemId") Long itemId) {
    itemService.copyItem(itemId);
    return "redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList";
  }

  /**
   * Method return list priorities for item radiobutton.
   *
   * @return priorityList
   */
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

  /**
   * Method returns the Wish List of the user who created the event.
   *
   * @param userId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/user-{id}/wishList", method = RequestMethod.GET)
  public String userWishList(@PathVariable("id") Long userId, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    model.addAttribute("ownerId", userId);
    model.addAttribute("wishList", itemService.getWishList(userId));
    model.addAttribute("popularItems", itemService.popularItems());
    model.addAttribute("popularTags", itemService.popularTags());

    return "item/personWishList";
  }

  /**
   * Method returns the Wish List of the user who created the event.
   *
   * @param creator
   * @param model
   * @return view
   */
  @RequestMapping(value = "/event-{eventId}-{creator}/wishList", method = RequestMethod.GET)
  public String eventWishList(@PathVariable("eventId") Long eventId, @PathVariable("creator") Long creator, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    model.addAttribute("eventId", eventId);
    model.addAttribute("ownerId", creator);
    Logger.getLogger(ItemController.class.getName()).info(itemService.getWishList(creator).toString());
    model.addAttribute("wishList", itemService.getWishList(creator));
    model.addAttribute("popularItems", itemService.popularItems());
    model.addAttribute("popularTags", itemService.popularTags());
    return "item/eventWishList";
  }

  @RequestMapping(value = "/event-{eventId}-{creator}/item-{itemId}/book", method = RequestMethod.GET)
  public String bookFromEvent(@PathVariable("itemId") Long itemId, @PathVariable("eventId") Long eventId, @PathVariable("creator") Long creator) {
    itemService.bookItemFromEvent(itemId, eventId);
    return "redirect:/account/event-" + eventId + "-" + creator + "/wishList";
  }

  /**
   * Method return item web page.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/item-{itemId}", method = RequestMethod.GET)
  public String item(@PathVariable("itemId") Long itemId, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    model.addAttribute("item", itemService.getItem(itemId));
    Long likes = itemService.countLikes(itemId);
    model.addAttribute("likes", likes);
    Boolean isLiked = itemService.isLiked(itemId, userService.getAuthenticatedUser().getId());
    model.addAttribute("isLiked", isLiked);
    return "item/viewItem";
  }

  /**
   * Method returns a web page with items that have a common tag.
   *
   * @param tagId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/search-tag/{tag}", method = RequestMethod.GET)
  public String searchByTag(@PathVariable("tag") Long tagId, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    model.addAttribute("items", itemService.getItemsByTag(tagId));
    return "item/searchByTag";
  }

  /**
   * If the user push Like button on the item web page,
   * the method {@code likeFromItem} updates the page and the item is added one like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/viewItem/like", method = RequestMethod.POST)
  public String likeFromItem(@RequestParam(value = "itemId") Long itemId, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.like(itemId, userService.getAuthenticatedUser().getId());
    return "redirect:/account/item-" + itemId;
  }

  /**
   * If the user push Dislike button on the item web page,
   * the method {@code dislikeFromItem} updates the page and the item is deleted like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/viewItem/dislike", method = RequestMethod.POST)
  public String dislikeFromItem(@RequestParam(value = "itemId") Long itemId, Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.dislike(itemId, userService.getAuthenticatedUser().getId());
    return "redirect:/account/item-" + itemId;
  }

  /**
   * If the user push Like button on the 'personWishList' web page,
   * the method {@code likeFromPersonWishList} updates the page and the item is added one like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/personWishList/like", method = RequestMethod.GET)
  public String likeFromPersonWishList(@RequestParam(value = "itemId") Long itemId,
                                       @RequestParam(value = "ownerId") Long ownerId,
                                       Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.like(itemId, userService.getAuthenticatedUser().getId());

    return "redirect:/account/user-" + ownerId + "/wishList";

  }

  /**
   * If the user push Like button on the 'personWishList' web page,
   * the method {@code dislikeFromPersonWishList} updates the page and the item is deleted like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/personWishList/dislike", method = RequestMethod.GET)
  public String dislikeFromPersonWishList(@RequestParam(value = "itemId") Long itemId, @RequestParam(value = "ownerId") Long ownerId,
                                          Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.dislike(itemId, userService.getAuthenticatedUser().getId());
    return "redirect:/account/user-" + ownerId + "/wishList";
  }

  /**
   * If the user push Like button on the 'personWishList' web page,
   * the method {@code likeFromEventWishList} updates the page and the item is added one like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/eventWishList/like", method = RequestMethod.POST)
  public String likeFromEventWishList(@RequestParam(value = "itemId") Long itemId,
                                      @RequestParam(value = "eventId") Long eventId,
                                      @RequestParam(value = "ownerId") Long creator,
                                      @RequestParam(value = "userId") Long userId,
                                      Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.like(itemId, userService.getAuthenticatedUser().getId());
    return "redirect:/account/event-" + eventId + "-" + creator + "/wishList";
  }

  /**
   * If the user push Like button on the 'eventWishList' web page,
   * the method {@code dislikeFromEventWishList} updates the page and the item is deleted like.
   *
   * @param itemId
   * @param model
   * @return view
   */
  @RequestMapping(value = "/eventWishList/dislike", method = RequestMethod.POST)
  public String dislikeFromEventWishList(@RequestParam(value = "itemId") Long itemId,
                                         @RequestParam(value = "eventId") Long eventId,
                                         @RequestParam(value = "ownerId") Long creator,
                                         @RequestParam(value = "userId") Long userId,
                                         Model model) {
    model.addAttribute("auth_user", userService.getAuthenticatedUser());
    itemService.dislike(itemId, userId);
    return "redirect:/account/event-" + eventId + "-" + creator + "/wishList";
  }
}
