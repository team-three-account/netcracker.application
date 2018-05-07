package com.gmail.netcracker.application.dto.dao.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.FolderDao;
import com.gmail.netcracker.application.dto.model.Folder;
import com.gmail.netcracker.application.dto.model.Note;
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

    private final RowMapper<Folder> folderRowMapper;
    private final RowMapper<Note> notesIntoFolderRowMapper;

    public FolderDaoImpl(DataSource dataSource, RowMapper<Folder> folderRowMapper, RowMapper<Note> notesIntoFolderRowMapper) {
        super(dataSource);
        this.folderRowMapper = folderRowMapper;
        this.notesIntoFolderRowMapper = notesIntoFolderRowMapper;
    }

    @Override
    public void createFolder(Folder folder) {
        insertEntity(SQL_ADD, PK_COLUMN_NAME,
                folder.getName(),
                folder.getCreator());
    }

    @Override
    public List<Folder> folderList() {
        return findEntityList(SQL_FIND_LIST_FOLDERS, folderRowMapper);
    }

    @Override
    public Folder getFolder(int folderId) {
        return findEntity(SQL_FIND, folderRowMapper, folderId);
    }

    @Override
    public void delete(int folderId) {
        deleteEntity(SQL_DELETE, folderId);
    }

    @Override
    public void update(Folder folder) {
        updateEntity(SQL_UPDATE,
                folder.getName(),
                folder.getFolderId());
    }

    @Override
    public List<Note> getNoteListIntoFolder(int folderId) {
        return findEntityList(SQL_GET_NOTES, notesIntoFolderRowMapper, folderId);
    }
}
