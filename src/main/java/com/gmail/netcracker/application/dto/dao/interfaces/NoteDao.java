package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Note;

import java.util.List;

public interface NoteDao {
    void insertNote(Note note);

    List<Note> noteList(Long creator);

    Note getNote(Long noteId);

    void delete(Long noteId);

    void update(Note note);

    void addNoteToFolder(Long noteId, Long folderId);

    void setFoldersNull(Long folderId);

    void deleteFromFolder(Long noteId);

    void addNoteToFolderBtn(Note note);
}
