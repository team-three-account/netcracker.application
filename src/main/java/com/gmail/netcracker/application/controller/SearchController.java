package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.SearchService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/account")
public class SearchController {

    @Autowired
    private UserService userService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private FriendService friendService;

    /**
     * This method get search query and type of search
     * And redirect to necessary controller
     *
     * @param modelAndView
     * @param search
     * @param typeSearch
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView mainSearch(ModelAndView modelAndView,
                                   @RequestParam("search") String search,
                                   @RequestParam("typeSearch") String typeSearch,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        if (typeSearch.equals("user")) {
            modelAndView.setViewName("redirect: /account/search/users");
            return modelAndView;
        }
        if (typeSearch.equals("event")) {
            modelAndView.setViewName("redirect: /account/search/events");
            return modelAndView;
        }
        if (typeSearch.equals("item")) {
            modelAndView.setViewName("redirect: /account/search/items");
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * This method return result search of Item
     * Represent a page with users items in upper block and system items in bottom block.
     *
     * @param model
     * @param search
     * @return
     */
    @RequestMapping(value = "/search/items")
    public String getSearchItem(Model model, String search) {
        if (search == null || search.isEmpty())
            return "redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList";
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("resultSearchMyItem", searchService.searchMyItems(search, userService.getAuthenticatedUser()));
        model.addAttribute("resultSearchItem", searchService.searchItems(search, userService.getAuthenticatedUser()));
        return "item/resultSearch";
    }

    /**
     * This method return result search of Events
     * Represent a page with users events in upper block and public events in bottom block.
     *
     * @param model
     * @param search
     * @return
     */
    @RequestMapping(value = "/search/events")
    public String getSearchEvents(Model model, String search)
    {
        if (search == null || search.isEmpty()) return "redirect:/account/eventList";
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("resultSearchPublic", searchService.searchPublicEvents(search, userService.getAuthenticatedUser()));
        model.addAttribute("resultSearchUserEvents", searchService.searchUserEvents(search, userService.getAuthenticatedUser()));
        return "event/resultSearch";
    }

    /**
     * Search in users by name or (and) surname.
     * Represent a page with appropriate friends in upper block and appropriate users not in friends in bottom block.
     * @param model Interface {@link Model}
     * @param search a string entered by user to specify keywords of search
     * @return Model
     */
    @RequestMapping(value = "/search/users")
    public String getSearchUser(Model model, String search) {
        if (search == null || search.isEmpty()) {
            return "redirect:/account/friends";
        }
        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", authUser);
        model.addAttribute("friendList", friendService.searchFriends(authUser.getId(), search));
        model.addAttribute("subtractionUsers", friendService.searchUsers(authUser.getId(), search));
        return "friend/friends";
    }
}
