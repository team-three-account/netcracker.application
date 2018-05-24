package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;

import com.gmail.netcracker.application.validation.FolderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/account")
public class FolderController {
    private final FolderService folderService;

    private final UserService userService;

    private final FolderValidator folderValidator;

    private final FriendService friendService;

    @Autowired
    public FolderController(FolderService folderService, UserService userService, FolderValidator folderValidator, FriendService friendService) {
        this.folderService = folderService;
        this.userService = userService;
        this.folderValidator = folderValidator;
        this.friendService = friendService;
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.GET)
    public ModelAndView createFolder(@ModelAttribute(value = "createFolder") Folder folder, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/createFolder");
        return modelAndView;
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    public ModelAndView saveFolder(@ModelAttribute("createFolder") Folder folder, BindingResult result,
                                   ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/createFolder");
        folderValidator.validate(folder, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        folderService.createFolder(folder);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }

    @RequestMapping(value = "/folder-{folderId}", method = RequestMethod.GET)
    public ModelAndView viewFolder(@PathVariable("folderId") Long folderId, ModelAndView modelAndView) {
        List<Note> noteList = folderService.getNoteListIntoFolder(folderId);
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("folder", folderService.getFolder(folderId));
        modelAndView.addObject("user_creator", userService.findUserById(folderService.getFolder(folderId).getCreator()));
        modelAndView.addObject("listNotesIntoFolder", noteList);
        modelAndView.addObject("message", folderMessage((long) noteList.size()));
        modelAndView.setViewName("folder/viewFolder");
        return modelAndView;
    }

    @RequestMapping(value = {"/deleteFolder-{folderId}"}, method = RequestMethod.GET)
    public String deleteFolder(@PathVariable Long folderId) {
        folderService.delete(folderId);
        return "redirect:/account/allNotes";
    }

    @RequestMapping(value = {"/editFolder-{folderId}"}, method = RequestMethod.GET)
    public ModelAndView editFolder(@PathVariable Long folderId, ModelAndView modelAndView) {
        modelAndView.addObject("editFolder", folderService.getFolder(folderId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/updateFolder");
        return modelAndView;
    }

    @RequestMapping(value = {"/editFolder-{folderId}"}, method = RequestMethod.POST)
    public ModelAndView updateFolder(@ModelAttribute("editFolder") Folder folder, BindingResult result,
                                     ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/updateFolder");
        folderValidator.validate(folder, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        folderService.update(folder);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }

    @RequestMapping(value = {"/share-{folderId}"}, method = RequestMethod.GET)
    public String viewFriendsToShareFolder(@PathVariable Long folderId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("folderId", folderId);
        List<User> friendsThatHaveAccessList = folderService.getFriendsThatHaveAccess(folderId);
        if (!friendsThatHaveAccessList.isEmpty())
            model.addAttribute("messageForAlreadyShared", "Friends that have access to folder :");
        model.addAttribute("friendsThatHaveAccessList", friendsThatHaveAccessList);
        List<User> friendsToShareList = folderService.getFriendsToShare(friendsThatHaveAccessList);
        if (!friendsToShareList.isEmpty())
            model.addAttribute("messageToShare", "You can allow access for following users :");
        model.addAttribute("friendsToShareList", friendsToShareList);
        return "folder/shareToFriends";
    }

    @RequestMapping(value = {"/share-{folderId}/share"}, method = RequestMethod.POST)
    public String allowAccessToFolder(@PathVariable Long folderId, @RequestParam(value = "userId") Long userId) {
        folderService.allowAccessToFolder(folderId, userId);
        return "redirect:/account/share-" + folderId;
    }

    @RequestMapping(value = {"/share-{folderId}/disable"}, method = RequestMethod.POST)
    public String disableAccessToFolder(@PathVariable Long folderId, @RequestParam(value = "friendId") Long friendId) {
        folderService.disableAccessToFolder(folderId, friendId);
        return "redirect:/account/share-" + folderId;
    }

    private static String folderMessage(Long sizeNoteList) {
        String clearFolder = "Notes into this folder: ";
        String folderHaveNotes = "Folder don't have any notes";
        return sizeNoteList > 0 ? clearFolder : folderHaveNotes;
    }

    @RequestMapping(value = {"/sharedFoldersToMe"}, method = RequestMethod.GET)
    public String sharedFoldersToMe(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("folderList", folderService.sharedFoldersToMe());
        return "folder/sharedToMe";
    }
}
