package com.gmail.netcracker.application.service.imp;


import com.gmail.netcracker.application.dto.dao.interfaces.NoteDao;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Autowired
    private UserService userService;

    @Override
    public void insertNote(Note note) {
        note.setCreator(userService.getAuthenticatedUser().getId());
        noteDao.insertNote(note);
    }

    @Override
    public List<Note> noteList() {
        List<Note> listNoteWithoutFolder = noteDao.noteList(userService.getAuthenticatedUser().getId());
        listNoteWithoutFolder.removeIf(note -> note.getFolder() > 0);
        return listNoteWithoutFolder;
    }

    @Override
    public Note getNote(Long noteId) {
        return noteDao.getNote(noteId);
    }

    @Override
    public void delete(Long noteId) {
        noteDao.delete(noteId);
    }

    @Override
    public void update(Note note) {
        note.setCreator(userService.getAuthenticatedUser().getId());
        noteDao.update(note);
    }

    @Override
    public void addNoteToFolder(int noteId, int folderId) {
        noteDao.addNoteToFolder(noteId, folderId);
    }

    @Override
    public void deleteFromFolder(Long noteId) {
        noteDao.deleteFromFolder(noteId);
    }

    @Override
    public void addNoteToFolderBtn(Note note) {
        noteDao.addNoteToFolderBtn(note);
    }
}
