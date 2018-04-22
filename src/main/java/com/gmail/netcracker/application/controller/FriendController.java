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

import java.util.List;

@Controller
@RequestMapping(value = "/account")
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
        return "account/friends";
    }

    @RequestMapping(value = "/{friend_id}", method = RequestMethod.GET)
    public String friendAccount(Model model, @PathVariable(value = "friend_id") String friend_id){
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "account/profile";
    }

    public static String amountOfFriendsMessage(int amount){
        String noFriend = "You have not any friend yet";
        String oneFriend = "You have 1 friend";
        String manyFriends = "You have " +amount+ " friends";
        String message = amount < 1 ? noFriend : amount == 1 ? oneFriend : manyFriends;
        return message;
    }
}
