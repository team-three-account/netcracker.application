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

import java.util.List;

@Controller
public class FriendController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String viewFriends(Model model) {
        User auth_user = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", auth_user);
        List<User> friendList = friendService.getAllFriends(auth_user.getId());
        model.addAttribute("friendList", friendList);
        model.addAttribute("message", amountOfFriendsMessage(friendList.size()));
        return "friend/friends";
    }

    @RequestMapping(value = "/{friend_id}", method = RequestMethod.GET)
    public String friendAccount(Model model, @PathVariable(value = "friend_id") String friend_id){
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "friend/profile";
    }

    @RequestMapping(value = "delete-friend", method = RequestMethod.POST)
    public String deleteFriend(@RequestParam(value = "friend_id") String friend_id){
         userService.getAuthenticatedUser().getId();

        return "redirect:/friend/friends";
    }

    @RequestMapping(value = "/friends/add-friend", method = RequestMethod.POST)
    public String addFriend(@RequestParam(value = "friend_id") String friend_id){

        friendService.addFriend(userService.getAuthenticatedUser().getId(), friend_id);
        return "/friend/outgoingRequest";
    }

    @RequestMapping(value = "/friends/add", method = RequestMethod.GET)
    public String searchFriend(Model model){
        model.addAttribute("auth_user", userService.getAuthenticatedUser());

        return "friend/addFriend";
    }

    @RequestMapping(value = "/friends/add", method = RequestMethod.POST)
    public String getSearch(Model model, String search){
        if(search.isEmpty()) return "friend/addFriend";
        List<User> foundFriends = friendService.searchFriends(search);
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("foundFriends",foundFriends);
        return "friend/addFriend";
    }

    public static String amountOfFriendsMessage(int amount){
        String noFriend = "You have not any friend yet";
        String oneFriend = "You have 1 friend";
        String manyFriends = "You have " +amount+ " friends";
        String message = amount < 1 ? noFriend : amount == 1 ? oneFriend : manyFriends;
        return message;
    }
}
