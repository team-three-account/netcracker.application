package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Note;

import java.util.List;

public interface NoteDao {
    void insertNote(Note note);

    List<Note> noteList();

    Note getNote(int noteId);
}
