package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;

import java.util.List;

public interface FolderService {
    void createFolder(Folder folder);

    List<Folder> folderList();

    Folder getFolder(int folderId);

    void delete(int folderId);

    void update(Folder folder);

    List<Note> getNoteListIntoFolder(int folderId);

}
