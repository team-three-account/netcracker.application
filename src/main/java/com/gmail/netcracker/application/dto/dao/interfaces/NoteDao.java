package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Note;

import java.util.List;

public interface NoteDao {
    void insertNote(Note note);

    List<Note> noteList(Long creator);

    Note getNote(Long noteId);

    void delete(Long noteId);

    void update(Note note);

    void addNoteToFolder(int noteId, int folderId);

    void setFoldersNull(Integer folderId);

    void deleteFromFolder(Long noteId);

    void addNoteToFolderBtn(Note note);
}
