package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
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

/**
 * Class controller for pages that manage friends
 */

@RequestMapping(value = "/account")
@Controller
public class FriendController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailConstructor emailConstructor;
    @Autowired
    private FriendService friendService;

    private Logger logger = Logger.getLogger(FriendController.class.getName());

    /**
     * This method returns  page with friends for authenticated user.
     * @param model Interface {@link Model}
     * @return Model
     */
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String viewFriends(Model model) {
        List<User> friendList = friendService.getAllFriends(userService.getAuthenticatedUser().getId());
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("friendList", friendList);
        model.addAttribute("message", amountOfFriendsMessage((long) friendList.size()));
        return "friend/friends";
    }

    /**
     * This method returns page of determined user.
     * @param friendId Object class {@link Long}
     * @param model Interface {@link Model}
     * @return Model
     */
    @RequestMapping(value = "/{friendId}", method = RequestMethod.GET)
    public ModelAndView friendAccount(@PathVariable(value = "friendId") Long friendId, ModelAndView model) {
        model.addObject("auth_user", userService.getAuthenticatedUser());
        model.addObject("friend", userService.findUserById(friendId));
        model.setViewName("friend/profile");
        return model;
    }

    /**
     * This is POST method for deleting determined user from friends.
     * @param friendId Object class {@link Long}
     * @return Model
     */
    @RequestMapping(value = "/delete-friend", method = RequestMethod.POST)
    public String deleteFriend(@RequestParam(value = "friendId") String friendId) {
        friendService.deleteFriend(userService.getAuthenticatedUser().getId(), Long.parseLong(friendId));
        return "redirect:/account/friends";
    }

    /**
     * This is POST method for adding determined user to friends.
     * @param friendId Object class {@link Long}
     * @return Model
     */
    @RequestMapping(value = "/friends/add-friend", method = RequestMethod.POST)
    public String addFriend(@RequestParam(value = "friendId") Long friendId) {
        friendService.addFriend(userService.getAuthenticatedUser().getId(), friendId);
        emailConstructor.notifyNewFriendEmailSender(userService.getAuthenticatedUser(), friendId);
        return "redirect:/account/friends/outgoing";
    }

    /**
     * This method returns page to view outgoing request to friends for authenticated user.
     * @param model Interface {@link Model}
     * @return Model
     */
    @RequestMapping(value = "/friends/outgoing", method = RequestMethod.GET)
    public String outgoingRequests(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> outgoingList = friendService.getOutgoingRequests(userService.getAuthenticatedUser().getId());
        model.addAttribute("outgoingList", outgoingList);
        if (outgoingList.isEmpty()) model.addAttribute("message", "You have not any outgoing request");

        return "friend/outgoingRequest";
    }

    /**
     * This is POST method that cancel request to friends
     * @param model Interface {@link Model}
     * @param friendId Object class {@link Long}
     * @return Model
     */
    @RequestMapping(value = "/friends/cancel-request", method = RequestMethod.POST)
    public String cancelRequest(Model model, @RequestParam(value = "friendId") Long friendId) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        friendService.cancelRequest(userService.getAuthenticatedUser().getId(), friendId);
        return "redirect:/account/friends/outgoing";
    }

    /**
     * This method returns page to view incoming request to friends for authenticated user.
     * @param model Interface {@link Model}
     * @return Model
     */
    @RequestMapping(value = "/friends/incoming", method = RequestMethod.GET)
    public String incomingRequests(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        List<User> incomingList = friendService.getIncomingRequests(userService.getAuthenticatedUser().getId());
        model.addAttribute("incomingList", incomingList);
        if (incomingList.isEmpty()) model.addAttribute("message", "You have not any incoming request");
        return "friend/incomingRequest";
    }

    /**
     * This is POST method that accept request to friends
     * @param model Interface {@link Model}
     * @param friendId Object class {@link Long}
     * @return Model
     */
    @RequestMapping(value = "/friends/accept-request", method = RequestMethod.POST)
    public String acceptRequest(Model model, @RequestParam(value = "friendId") Long friendId) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        friendService.acceptRequest(userService.getAuthenticatedUser().getId(), friendId);
        return "redirect:/account/friends/incoming";
    }

    /**
     * This method returns message to display depending on amount of friends
     * @param amount Object class {@link Long}
     * @return String to display on .jsp page
     */
    private static String amountOfFriendsMessage(Long amount) {
        String noFriend = "You have not any friend yet";
        String oneFriend = "You have 1 friend";
        String manyFriends = "You have " + amount + " friends";
        return amount < 1 ? noFriend : amount == 1 ? oneFriend : manyFriends;
    }
}

