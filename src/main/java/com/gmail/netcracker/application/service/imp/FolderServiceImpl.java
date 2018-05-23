package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FolderDao;
import com.gmail.netcracker.application.dto.dao.interfaces.NoteDao;
import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.FolderService;
import com.gmail.netcracker.application.service.interfaces.FriendService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderDao folderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private NoteDao noteDao;

    @Override
    public void createFolder(Folder folder) {
        folder.setCreator(userService.getAuthenticatedUser().getId());
        folderDao.createFolder(folder);
    }

    @Override
    public List<Folder> folderList() {
        return folderDao.folderList(userService.getAuthenticatedUser().getId());
    }

    @Override
    public Folder getFolder(Long folderId) {
        return folderDao.getFolder(folderId);
    }

    @Override
    public void delete(Long folderId) {
        noteDao.setFoldersNull(folderId);
        folderDao.delete(folderId);
    }

    @Override
    public void update(Folder folder) {
        folderDao.update(folder);
    }

    @Override
    public List<Note> getNoteListIntoFolder(Long folderId) {
        return folderDao.getNoteListIntoFolder(folderId);
    }

    @Override
    public List<User> getFriendsToShare(List<User> friendsThatHaveAccessList) {
        List<User> friendList = friendService.getAllFriends(userService.getAuthenticatedUser().getId());
        for (User item : friendsThatHaveAccessList) {
            if (friendList.contains(item)) {
                friendList.remove(item);
            }
        }
        return friendList;
    }

    @Override
    public List<User> getFriendsThatHaveAccess(Long folderId) {
        return folderDao.getFriendsThatHaveAccess(folderId);
    }

    @Override
    public void allowAccessToFolder(Long folderId, Long userId) {
        folderDao.allowAccessToFolder(folderId, userId);
    }

    @Override
    public void disableAccessToFolder(Long folderId, Long friendId) {
        folderDao.disableAccessToFolder(folderId, friendId);
    }

    @Override
    public List<Folder> sharedFoldersToMe() {
        return folderDao.getSharedFoldersToMe(userService.getAuthenticatedUser().getId());
    }

}
