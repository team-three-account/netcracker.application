package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.NoteValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/account")
public class NoteController {

    private final NoteService noteService;

    private final UserService userService;

    private final NoteValidator noteValidator;

    public NoteController(NoteService noteService, UserService userService, NoteValidator noteValidator) {
        this.noteService = noteService;
        this.userService = userService;
        this.noteValidator = noteValidator;
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
        noteValidator.validate(note, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        noteService.insertNote(note);
        modelAndView.setViewName("redirect:/account/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "/eventList/note-{noteId}", method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable("noteId") int noteId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("note", noteService.getNote(noteId));
        modelAndView.addObject("user_creator", userService.findUserById(noteService.getNote(noteId).getCreator()));
        modelAndView.setViewName("note/viewNote");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/deleteNote-{noteId}"}, method = RequestMethod.GET)
    public String deleteNote(@PathVariable int noteId) {
        noteService.delete(noteId);
        return "redirect:/account/eventlist";
    }

    @RequestMapping(value = {"/eventList/editNote-{noteId}"}, method = RequestMethod.GET)
    public ModelAndView editEvent(@PathVariable int noteId, ModelAndView modelAndView) {
        modelAndView.addObject("editNote", noteService.getNote(noteId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/updateNote");
        return modelAndView;
    }

    @RequestMapping(value = {"/eventList/editNote-{noteId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@ModelAttribute("editNote") Note note, BindingResult result,
                                    ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("note/updateNote");
        noteValidator.validate(note, result);
        if (result.hasErrors()) {
            return modelAndView;
        }
        noteService.update(note);
        modelAndView.setViewName("redirect:/account/eventlist");
        return modelAndView;
    }

    @RequestMapping(value = "/allNotes", method = RequestMethod.GET)
    public String allNotes(Model model) {
        User authUser =userService.getAuthenticatedUser();
        model.addAttribute("auth_user", authUser);
        model.addAttribute("noteList", noteService.noteList());
        return "note/allNotes";
    }

}
