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
        return folderDao.folderList();
    }

    @Override
    public Folder getFolder(int folderId) {
        return folderDao.getFolder(folderId);
    }

    @Override
    public void delete(int folderId) {
        noteDao.setFoldersNull(folderId);
        folderDao.delete(folderId);
    }

    @Override
    public void update(Folder folder) {
        folderDao.update(folder);
    }

    @Override
    public List<Note> getNoteListIntoFolder(int folderId) {
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
    public List<User> getFriendsThatHaveAccess(int folderId) {
        return folderDao.getFriendsThatHaveAccess(folderId);
    }

    @Override
    public void allowAccessToFolder(int folderId, int userId) {
        folderDao.allowAccessToFolder(folderId, userId);
    }

    @Override
    public void disableAccessToFolder(int folderId, int friendId) {
        folderDao.disableAccessToFolder(folderId, friendId);
    }

}
