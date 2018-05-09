package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.UserService;

import com.gmail.netcracker.application.validation.FolderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class FolderController {
    private final FolderService folderService;

    private final UserService userService;

    private final FolderValidator folderValidator;

    @Autowired
    public FolderController(FolderService folderService, UserService userService, FolderValidator folderValidator) {
        this.folderService = folderService;
        this.userService = userService;
        this.folderValidator = folderValidator;
    }

    @RequestMapping(value = "/eventList/createFolder", method = RequestMethod.GET)
    public ModelAndView createNote(@ModelAttribute(value = "createFolder") Folder folder, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/createFolder");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createFolder", method = RequestMethod.POST)
    public ModelAndView saveNote(@ModelAttribute("createFolder") Folder folder, BindingResult result,
                                 ModelAndView modelAndView) {
        modelAndView.setViewName("folder/createFolder");
        folderValidator.validate(folder, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        folderService.createFolder(folder);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/folder-{folderId}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("folderId") int folderId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("folder", folderService.getFolder(folderId));
        modelAndView.addObject("user_creator", userService.findUserById(folderService.getFolder(folderId).getCreator()));
        modelAndView.setViewName("folder/viewFolder");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/deleteFolder-{folderId}"}, method = RequestMethod.GET)
    public String deleteFolder(@PathVariable int folderId) {
        folderService.delete(folderId);
        return "redirect:/account/allNotes";
    }

    @RequestMapping(value = {"/eventList/editFolder-{folderId}"}, method = RequestMethod.GET)
    public ModelAndView editFolder(@PathVariable int folderId, ModelAndView modelAndView) {
        modelAndView.addObject("editFolder", folderService.getFolder(folderId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/updateFolder");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editFolder-{folderId}"}, method = RequestMethod.POST)
    public ModelAndView updateFolder(@ModelAttribute("editFolder") Folder folder,
                                     ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        folderService.update(folder);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }


}
