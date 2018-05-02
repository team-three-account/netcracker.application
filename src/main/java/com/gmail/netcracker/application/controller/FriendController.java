package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@RequestMapping(value = "/account")
@Controller
public class FriendController {

    private final UserService userService;

    private final FriendService friendService;

    @Autowired
    public FriendController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }


    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String viewFriends(Model model) {
        List<User> friendList = friendService.getAllFriends(userService.getAuthenticatedUser().getId());
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("friendList", friendList);
        model.addAttribute("message", amountOfFriendsMessage(friendList.size()));
        return "friend/friends";
    }

    @RequestMapping(value = "/{friend_id}", method = RequestMethod.GET)
    public ModelAndView friendAccount(@PathVariable(value = "friend_id") String friend_id, ModelAndView model) {
        model.addObject("auth_user", userService.getAuthenticatedUser());
        model.addObject("friend", userService.findUserById(Long.parseLong(friend_id)));
        model.setViewName("friend/profile");
        return model;
    }

    @RequestMapping(value = "/delete-friend", method = RequestMethod.POST)
    public String deleteFriend(@RequestParam(value = "friend_id") String friend_id) {
        friendService.deleteFriend(userService.getAuthenticatedUser().getId(), Long.parseLong(friend_id));
        return "redirect:/account/friends";
    }

    @RequestMapping(value = "/friends/add-friend", method = RequestMethod.POST)
    public String addFriend(@RequestParam(value = "friend_id") Long friend_id) {
        friendService.addFriend(userService.getAuthenticatedUser().getId(), friend_id);
        return "redirect:/account/friends/outgoing";
    }

    @RequestMapping(value = "/friends/search", method = RequestMethod.POST)
    public String getSearch(Model model, String search) {
        if (search.isEmpty()) {
            return "redirect:/account/friends";
        }
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> friendList = friendService.searchFriends(userService.getAuthenticatedUser().getId(), search);
        model.addAttribute("friendList", friendList);
        List<User> intersectionUsers = friendService.intersect(friendList, friendService.searchUsers(userService.getAuthenticatedUser().getId(), search));
        model.addAttribute("intersectionUsers", intersectionUsers);
        return "friend/friends";
    }

    @RequestMapping(value = "/friends/outgoing", method = RequestMethod.GET)
    public String outgoingRequests(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> outgoingList = friendService.getOutgoingRequests(userService.getAuthenticatedUser().getId());
        model.addAttribute("outgoingList", outgoingList);
        if (outgoingList.isEmpty()) model.addAttribute("message", "You have not any outgoing request");

        return "friend/outgoingRequest";
    }

    @RequestMapping(value = "/friends/cancel-request", method = RequestMethod.POST)
    public String cancelRequest(Model model, @RequestParam(value = "friend_id") Long friend_id) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        friendService.cancelRequest(userService.getAuthenticatedUser().getId(), friend_id);
        return "redirect:/account/friends/outgoing";
    }

    @RequestMapping(value = "/friends/incoming", method = RequestMethod.GET)
    public String incomingRequests(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> incomingList = friendService.getIncomingRequests(userService.getAuthenticatedUser().getId());
        model.addAttribute("incomingList", incomingList);
        if (incomingList.isEmpty()) model.addAttribute("message", "You have not any incoming request");
        return "friend/incomingRequest";
    }

    @RequestMapping(value = "/friends/accept-request", method = RequestMethod.POST)
    public String acceptRequest(Model model, @RequestParam(value = "friend_id") Long friend_id) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        friendService.acceptRequest(userService.getAuthenticatedUser().getId(), friend_id);
        return "redirect:/account/friends/incoming";
    }

    private static String amountOfFriendsMessage(int amount) {
        String noFriend = "You have not any friend yet";
        String oneFriend = "You have 1 friend";
        String manyFriends = "You have " + amount + " friends";
        String message = amount < 1 ? noFriend : amount == 1 ? oneFriend : manyFriends;
        return message;
    }
}
