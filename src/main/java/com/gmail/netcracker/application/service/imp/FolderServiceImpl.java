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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class is a folder service which connects DAO layer and controller.
 */
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

    /**
     * The method set creator when customer create folder.
     *
     * @param folder
     */
    @Override
    @Transactional
    public void createFolder(Folder folder) {
        folder.setCreator(userService.getAuthenticatedUser().getId());
        folderDao.createFolder(folder);
    }

    /**
     * The method returns customer folder list.
     *
     * @return
     */
    @Override
    public List<Folder> folderList() {
        return folderDao.folderList(userService.getAuthenticatedUser().getId());
    }

    @Override
    public Folder getFolder(Long folderId) {
        return folderDao.getFolder(folderId);
    }


    @Override
    @Transactional
    public void delete(Long folderId) {
        noteDao.setFoldersNull(folderId);
        folderDao.disableAccessForShared(folderId);
        folderDao.delete(folderId);
    }

    @Override
    @Transactional
    public void update(Folder folder) {
        folderDao.update(folder);
    }

    @Override
    public List<Note> getNoteListIntoFolder(Long folderId) {
        return folderDao.getNoteListIntoFolder(folderId);
    }

    @Override
    public List<User> getFriendsToShare(Long folderId) {
        return folderDao.getFriendsToShare(folderId, userService.getAuthenticatedUser().getId());
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
