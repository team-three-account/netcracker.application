package com.gmail.netcracker.application.service.imp;


import com.gmail.netcracker.application.dto.dao.interfaces.NoteDao;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    @Autowired
    Note note;

    @Autowired
    NoteDao noteDao;

    @Autowired
    UserService userService;

    @Override
    public void insertNote(Note note) {
        note.setCreator(userService.getAuthenticatedUser().getId());
        noteDao.insertNote(note);
    }

    @Override
    public List<Note> noteList() {
        return noteDao.noteList();
    }

    @Override
    public Note getNote(int noteId) {
        return noteDao.getNote(noteId);
    }
}
