package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Note;
import java.util.List;

public interface NoteService {
    void insertNote(Note note);

    List<Note> noteList();

    Note getNote(Long noteId);

    void delete(Long noteId);

    void update(Note note);

    void addNoteToFolder(int noteId, int folderId);

    void deleteFromFolder(Long noteId);
}
