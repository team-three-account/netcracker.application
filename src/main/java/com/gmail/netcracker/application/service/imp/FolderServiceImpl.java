package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FolderDao;
import com.gmail.netcracker.application.dto.dao.interfaces.NoteDao;
import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.service.interfaces.FolderService;
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

}
