package com.gmail.netcracker.application.service.imp;


import com.gmail.netcracker.application.dto.dao.interfaces.NoteDao;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.service.interfaces.NoteService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class is a note service which connects DAO layer and controller.
 */

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Autowired
    private UserService userService;

    /**
     * This method set creator and calls dao layer when someone create note.
     *
     * @param note
     */
    @Override
    @Transactional
    public void insertNote(Note note) {
        note.setCreator(userService.getAuthenticatedUser().getId());
        noteDao.insertNote(note);
    }

    /**
     * This method returns list notes without folder.
     *
     * @return List<Note>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Note> noteList() {
        List<Note> listNoteWithoutFolder = noteDao.noteList(userService.getAuthenticatedUser().getId());
        listNoteWithoutFolder.removeIf(note -> note.getFolder() > 0);
        return listNoteWithoutFolder;
    }


    @Override
    @Transactional(readOnly = true)
    public Note getNote(Long noteId) {
        return noteDao.getNote(noteId);
    }

    @Override
    @Transactional
    public void delete(Long noteId) {
        noteDao.delete(noteId);
    }

    /**
     * This method set creator when customer update note.
     *
     * @param note
     */

    @Override
    @Transactional
    public void update(Note note) {
        note.setCreator(userService.getAuthenticatedUser().getId());
        noteDao.update(note);
    }

    /**
     * This method allows add note to folder with js.
     *
     * @param noteId
     * @param folderId
     */
    @Override
    @Transactional
    public void addNoteToFolder(Long noteId, Long folderId) {
        noteDao.addNoteToFolder(noteId, folderId);
    }


    @Override
    @Transactional
    public void deleteFromFolder(Long noteId) {
        noteDao.deleteFromFolder(noteId);
    }

    /**
     * This method allows add note to folder.
     *
     * @param note
     */
    @Override
    @Transactional
    public void addNoteToFolderBtn(Note note) {
        noteDao.addNoteToFolderBtn(note);
    }
}
