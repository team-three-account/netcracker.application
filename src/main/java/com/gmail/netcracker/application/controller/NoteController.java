package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.NoteValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;


@Controller
@RequestMapping("/account")
public class NoteController {

    private final NoteService noteService;

    private final FolderService folderService;

    private final UserService userService;

    private final NoteValidator noteValidator;

    private final EventController eventController;

    public NoteController(NoteService noteService, FolderService folderService, UserService userService,
                          NoteValidator noteValidator, EventController eventController) {
        this.noteService = noteService;
        this.folderService = folderService;
        this.userService = userService;
        this.noteValidator = noteValidator;
        this.eventController = eventController;
    }

    @RequestMapping(value = "/eventList/createNote", method = RequestMethod.GET)
    public ModelAndView createNote(@ModelAttribute(value = "createNote") Note note, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/createNote");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/createNote", method = RequestMethod.POST)
    public ModelAndView saveNote(@ModelAttribute("createNote") Note note, BindingResult result,
                                 ModelAndView modelAndView) {
        modelAndView.setViewName("note/createNote");
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        noteValidator.validate(note, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        noteService.insertNote(note);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/note-{noteId}", method = RequestMethod.GET)
    public ModelAndView viewNote(@PathVariable("noteId") Long noteId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("note", noteService.getNote(noteId));
        modelAndView.addObject("user_creator", userService.findUserById(noteService.getNote(noteId).getCreator()));
        modelAndView.setViewName("note/viewNote");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/deleteNote-{noteId}"}, method = RequestMethod.GET)
    public String deleteNote(@PathVariable Long noteId) {
        noteService.delete(noteId);
        return "redirect:/account/allNotes";
    }

    @RequestMapping(value = {"/eventList/editNote-{noteId}"}, method = RequestMethod.GET)
    public ModelAndView editNote(@PathVariable Long noteId, ModelAndView modelAndView) {
        modelAndView.addObject("editNote", noteService.getNote(noteId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/updateNote");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editNote-{noteId}"}, method = RequestMethod.POST)
    public ModelAndView updateNote(@ModelAttribute("editNote") Note note, BindingResult result,
                                   ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/updateNote");
        noteValidator.validate(note, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        noteService.update(note);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }

    @RequestMapping(value = "/allNotes", method = RequestMethod.GET)
    public String allNotes(Model model) {
        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", authUser);
        model.addAttribute("folderList", folderService.folderList());
        model.addAttribute("noteList", noteService.noteList());
        return "note/allNotes";
    }

    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity moveNote(@RequestParam int folderId,
                                   @RequestParam int noteId) {
        noteService.addNoteToFolder(noteId, folderId);
        return ResponseEntity.ok("Note was moved to folder successfully.");
    }
}