package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.NoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is a note controller which connects business logic and web view through url patterns.
 */

@Controller
@RequestMapping("/account")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteValidator noteValidator;

    /**
     * This method returns to the web page with fields to be filled in order field to create a new note.
     *
     * @param note
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = "/createNote", method = RequestMethod.GET)
    public ModelAndView createNote(@ModelAttribute(value = "createNote") Note note, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/createNote");

        return modelAndView;
    }

    /**
     * This method calls validation for coming to server note fields.
     * Method creates a note if validation go through  successfully
     * otherwise method returns creation note web page.
     *
     * @param note
     * @param result
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = "/createNote", method = RequestMethod.POST)
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

    /**
     * This method transfers to the note web page.
     *
     * @param noteId
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = "/note-{noteId}", method = RequestMethod.GET)
    public ModelAndView viewNote(@PathVariable("noteId") Long noteId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("note", noteService.getNote(noteId));
        modelAndView.addObject("user_creator", userService.findUserById(noteService.getNote(noteId).getCreator()));
        modelAndView.setViewName("note/viewNote");
        return modelAndView;
    }

    /**
     * This method removes the note and redirect to the certain page.
     *
     * @param noteId
     * @return String
     */
    @RequestMapping(value = {"/deleteNote-{noteId}"}, method = RequestMethod.GET)
    public String deleteNote(@PathVariable Long noteId) {
        noteService.delete(noteId);
        return "redirect:/account/allNotes";
    }

    /**
     * This method removes the note from folder and redirect to the certain page.
     *
     * @param noteId
     * @return String
     */
    @RequestMapping(value = {"/deleteFF-{noteId}"}, method = RequestMethod.GET)
    public String deleteNoteFromFolder(@PathVariable Long noteId) {
        noteService.deleteFromFolder(noteId);
        return "redirect:/account/allNotes";
    }

    /**
     * This method returns note fields update web page.
     *
     * @param noteId
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = {"/editNote-{noteId}"}, method = RequestMethod.GET)
    public ModelAndView editNote(@PathVariable Long noteId, ModelAndView modelAndView) {
        modelAndView.addObject("editNote", noteService.getNote(noteId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/updateNote");
        return modelAndView;
    }

    /**
     * This method calls validation for coming to server note fields.
     * Method updates a note  if validation go through  successfully
     * otherwise method returns updating note web page.
     *
     * @param note
     * @param result
     * @param modelAndView
     * @return modelAndView
     */
    @RequestMapping(value = {"/editNote-{noteId}"}, method = RequestMethod.POST)
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

    /**
     * This method returns a web page where customer can see own notes.
     *
     * @param model
     * @return String
     */
    @RequestMapping(value = "/allNotes", method = RequestMethod.GET)
    public String allNotes(Model model) {
        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("auth_user", authUser);
        model.addAttribute("folderList", folderService.folderList());
        model.addAttribute("noteList", noteService.noteList());
        return "note/allNotes";
    }

    /**
     * This method allows move note into folder.
     *
     * @param folderId
     * @param noteId
     * @return ResponseEntity
     */
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity moveNote(@RequestParam Long folderId,
                                   @RequestParam Long noteId) {
        noteService.addNoteToFolder(noteId, folderId);
        return ResponseEntity.ok("Note was moved to folder successfully.");
    }

    /**
     * This method returns a web page where customer can select a folder.
     *
     * @param modelAndView
     * @param noteId
     * @return modelAndView
     */
    @RequestMapping(value = {"/add-note-{noteId}"}, method = RequestMethod.GET)
    public ModelAndView addNoteToFolderBtn(ModelAndView modelAndView, @PathVariable Long noteId) {
        Note note = noteService.getNote(noteId);
        modelAndView.addObject("newNoteIntoFolder", note);
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("listFolders", folderService.folderList());
        modelAndView.setViewName("note/addNoteToFolder");
        return modelAndView;
    }

    /**
     * This method save note into select folder.
     *
     * @param modelAndView
     * @param note
     * @return modelAndView
     */
    @RequestMapping(value = {"/add-note-{noteId}"}, method = RequestMethod.POST)
    public ModelAndView saveNoteToFolderBtn(ModelAndView modelAndView, @ModelAttribute("newNoteIntoFolder") Note note) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        noteService.addNoteToFolderBtn(note);
        modelAndView.setViewName("redirect:/account/allNotes");
        return modelAndView;
    }
}