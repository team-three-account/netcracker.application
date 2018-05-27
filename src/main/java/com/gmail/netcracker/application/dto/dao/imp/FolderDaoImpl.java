package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FolderDao;
import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
import com.gmail.netcracker.application.dto.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FolderDaoImpl extends ModelDao implements FolderDao {
    @Value("${sql.folder.pkColumnName}")
    private String PK_COLUMN_NAME = "folder_id";

    @Value("${sql.folder.add}")
    private String SQL_ADD;

    @Value("${sql.folder.find}")
    private String SQL_FIND;

    @Value("${sql.folder.delete}")
    private String SQL_DELETE;

    @Value("${sql.folder.update}")
    private String SQL_UPDATE;

    @Value("${sql.folder.findListFolders}")
    private String SQL_FIND_LIST_FOLDERS;

    @Value("${sql.folder.getNotesListIntoFolder}")
    private String SQL_GET_NOTES;

    @Value("${sql.folder.getFriendsThatHaveAccess}")
    private String SQL_GET_FRIENDS_THAT_HAVE_ACCESS;

    @Value("${sql.folder.allowAccessToFolder}")
    private String SQL_ALLOW_ACCESS_TO_FOLDER;

    @Value("${sql.folder.disableAccessToFolder}")
    private String SQL_DISABLE_ACCESS_TO_FOLDER;

    @Value("${sql.folder.getSharedFoldersToMe}")
    private String SQL_GET_SHARED_FOLDERS_TO_ME;

    @Value("${sql.folder.disableAccessForShared}")
    private String SQL_DISABLE_ACCESS_FOR_SHARED;

    private final RowMapper<Folder> folderRowMapper;
    private final RowMapper<Note> notesIntoFolderRowMapper;
    private final RowMapper<User> userRowMapper;

    public FolderDaoImpl(DataSource dataSource, RowMapper<Folder> folderRowMapper, RowMapper<Note> notesIntoFolderRowMapper,
                         RowMapper<User> userRowMapper) {
        super(dataSource);
        this.folderRowMapper = folderRowMapper;
        this.notesIntoFolderRowMapper = notesIntoFolderRowMapper;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public void createFolder(Folder folder) {
        insertEntity(SQL_ADD, PK_COLUMN_NAME,
                folder.getName(),
                folder.getCreator());
    }

    @Override
    public List<Folder> folderList(Long creator) {
        return findEntityList(SQL_FIND_LIST_FOLDERS, folderRowMapper, creator);
    }

    @Override
    public Folder getFolder(Long folderId) {
        return findEntity(SQL_FIND, folderRowMapper, folderId);
    }

    @Override
    public void delete(Long folderId) {
        deleteEntity(SQL_DELETE, folderId);
    }

    @Override
    public void update(Folder folder) {
        updateEntity(SQL_UPDATE,
                folder.getName(),
                folder.getFolderId());
    }

    @Override
    public List<Note> getNoteListIntoFolder(Long folderId) {
        return findEntityList(SQL_GET_NOTES, notesIntoFolderRowMapper, folderId);
    }

    @Override
    public List<User> getFriendsThatHaveAccess(Long folderId) {
        return findEntityList(SQL_GET_FRIENDS_THAT_HAVE_ACCESS, userRowMapper, folderId);
    }

    @Override
    public void disableAccessToFolder(Long folderId, Long friendId) {
        deleteEntity(SQL_DISABLE_ACCESS_TO_FOLDER, folderId, friendId);
    }

    @Override
    public void allowAccessToFolder(Long folderId, Long userId) {
        insertEntity(SQL_ALLOW_ACCESS_TO_FOLDER, PK_COLUMN_NAME,folderId, userId);
    }

    @Override
    public List<Folder> getSharedFoldersToMe(Long id) {
        return findEntityList(SQL_GET_SHARED_FOLDERS_TO_ME, folderRowMapper, id);
    }

    @Override
    public void disableAccessForShared(Long folderId) {
        deleteEntity(SQL_DISABLE_ACCESS_FOR_SHARED, folderId);
    }
}
