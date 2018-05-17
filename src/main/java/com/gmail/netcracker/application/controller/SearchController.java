package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.SearchService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class SearchController {
    @Autowired
    private User authUser;
    @Autowired
    private SearchService searchService;

    @Autowired
    private UserService userService;

    
    @RequestMapping(value = "/eventList/search", method = RequestMethod.POST)
    public String getSearch(Model model, String search) {
        if (search==null||search.isEmpty()) return "redirect:/account/eventList";
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("resultSearchPublic", searchService.searchPublicEvents(search, userService.getAuthenticatedUser()));
        model.addAttribute("resultSearchUserEvents", searchService.searchUserEvents(search, userService.getAuthenticatedUser()));
        return "event/resultSearch";
    }
}
