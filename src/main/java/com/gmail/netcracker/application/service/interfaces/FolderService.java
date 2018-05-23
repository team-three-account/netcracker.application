package com.gmail.netcracker.application.service.interfaces;

import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;

import java.util.List;

public interface FolderService {
    void createFolder(Folder folder);

    List<Folder> folderList();

    Folder getFolder(Long folderId);

    void delete(Long folderId);

    void update(Folder folder);

    List<Note> getNoteListIntoFolder(Long folderId);

    List<User> getFriendsToShare(List<User> friendsThatHaveAccessList);

    List<User> getFriendsThatHaveAccess(Long folderId);

    void allowAccessToFolder(Long folderId, Long userId);

    void disableAccessToFolder(Long folderId, Long friendId);

    List<Folder> sharedFoldersToMe();
}
