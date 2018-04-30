package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
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

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
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
}
