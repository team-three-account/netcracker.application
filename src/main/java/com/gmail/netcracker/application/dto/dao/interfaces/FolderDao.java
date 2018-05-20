package com.gmail.netcracker.application.dto.dao.interfaces;

import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FolderDao {
    void createFolder(Folder folder);

    List<Folder> folderList();

    Folder getFolder(int folderId);

    void delete(int folderId);

    void update(Folder folder);

    List<Note> getNoteListIntoFolder(int folderId);

    List<User> getFriendsThatHaveAccess(int folderId);

    void disableAccessToFolder(int folderId, int friendId);

    void allowAccessToFolder(int folderId, int userId);

    List<Folder> getSharedFoldersToMe(Long id);
}
