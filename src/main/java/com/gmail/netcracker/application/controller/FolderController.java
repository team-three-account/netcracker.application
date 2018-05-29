package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;

import com.gmail.netcracker.application.validation.FolderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * This class is a folder controller which connects business logic and web view through url patterns.
 */
@Controller
@RequestMapping("/account")
public class FolderController {
    @Autowired
    private FolderService folderService;
    @Autowired
    private UserService userService;
    @Autowired
    private FolderValidator folderValidator;

    /**
     * Method returns to the web page with fields to be filled in order field to create a new folder.
     *
     * @param folder
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = "/createFolder", method = RequestMethod.GET)
    public ModelAndView createFolder(@ModelAttribute(value = "createFolder") Folder folder, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/createFolder");
        return modelAndView;
    }

    /**
     * This method calls validation for coming to server folder fields.
     * Method creates a folder if validation go through  successfully
     * otherwise method returns creation folder web page.
     *
     * @param folder
     * @param result
     * @param modelAndView
     * @return modelAndView
     */
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

    /**
     * This method transfers to the folder content web page.
     *
     * @param folderId
     * @param modelAndView
     * @return modelAndView
     */
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

    /**
     * This method removes the folder and redirect to the certain page.
     *
     * @param folderId
     * @return String
     */
    @RequestMapping(value = {"/deleteFolder-{folderId}"}, method = RequestMethod.GET)
    public String deleteFolder(@PathVariable Long folderId) {
        folderService.delete(folderId);
        return "redirect:/account/allNotes";
    }

    /**
     * This method returns folder fields update web page.
     *
     * @param folderId
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = {"/editFolder-{folderId}"}, method = RequestMethod.GET)
    public ModelAndView editFolder(@PathVariable Long folderId, ModelAndView modelAndView) {
        modelAndView.addObject("editFolder", folderService.getFolder(folderId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("folder/updateFolder");
        return modelAndView;
    }

    /**
     * This method calls validation for coming to server folder fields.
     * Method updates a folder if validation go through  successfully
     * otherwise method returns updating folder web page.
     *
     * @param folder
     * @param result
     * @param modelAndView
     * @return modelAndView
     */
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

    /**
     * This method allows folder sharing with the list of friends.
     *
     * @param folderId
     * @param model
     * @return String
     */
    @RequestMapping(value = {"/share-{folderId}"}, method = RequestMethod.GET)
    public String viewFriendsToShareFolder(@PathVariable Long folderId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("folderId", folderId);
        List<User> friendsThatHaveAccessList = folderService.getFriendsThatHaveAccess(folderId);
        if (!friendsThatHaveAccessList.isEmpty())
            model.addAttribute("messageForAlreadyShared", "Friends that have access to folder :");
        model.addAttribute("friendsThatHaveAccessList", friendsThatHaveAccessList);
        List<User> friendsToShareList = folderService.getFriendsToShare(folderId);
        if (!friendsToShareList.isEmpty())
            model.addAttribute("messageToShare", "You can allow access for following users :");
        model.addAttribute("friendsToShareList", friendsToShareList);
        return "folder/shareToFriends";
    }

    /**
     * This method allows access to folder.
     *
     * @param folderId
     * @param userId
     * @return String
     */
    @RequestMapping(value = {"/share-{folderId}/share"}, method = RequestMethod.POST)
    public String allowAccessToFolder(@PathVariable Long folderId, @RequestParam(value = "userId") Long userId) {
        folderService.allowAccessToFolder(folderId, userId);
        return "redirect:/account/share-" + folderId;
    }

    /**
     * This method disable access to folder.
     *
     * @param folderId
     * @param friendId
     * @return String
     */
    @RequestMapping(value = {"/share-{folderId}/disable"}, method = RequestMethod.POST)
    public String disableAccessToFolder(@PathVariable Long folderId, @RequestParam(value = "friendId") Long friendId) {
        folderService.disableAccessToFolder(folderId, friendId);
        return "redirect:/account/share-" + folderId;
    }

    /**
     * This method checks folder content and replies correspondingly.
     *
     * @param sizeNoteList
     * @return String
     */
    private static String folderMessage(Long sizeNoteList) {
        String clearFolder = "Notes into this folder: ";
        String folderHaveNotes = "Folder don't have any notes";
        return sizeNoteList > 0 ? clearFolder : folderHaveNotes;
    }

    /**
     * This method returns a web page where customers can see shared folder.
     *
     * @param model
     * @return String
     */
    @RequestMapping(value = {"/sharedFoldersToMe"}, method = RequestMethod.GET)
    public String sharedFoldersToMe(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("folderList", folderService.sharedFoldersToMe());
        return "folder/sharedToMe";
    }
}
