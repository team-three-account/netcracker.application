package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FolderDao {
    void createFolder(Folder folder);

    List<Folder> folderList(Long creator);

    Folder getFolder(Long folderId);

    void delete(Long folderId);

    void update(Folder folder);

    List<Note> getNoteListIntoFolder(Long folderId);

    List<User> getFriendsThatHaveAccess(Long folderId);

    void disableAccessToFolder(Long folderId, Long friendId);

    void allowAccessToFolder(Long folderId, Long userId);

    List<Folder> getSharedFoldersToMe(Long id);
}
