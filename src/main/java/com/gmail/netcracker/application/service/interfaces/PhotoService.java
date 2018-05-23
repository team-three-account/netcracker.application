package com.gmail.netcracker.application.service.interfaces;

import com.dropbox.core.DbxException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PhotoService {

    String uploadFileOnDropBox(MultipartFile file, String name) throws IOException, DbxException;

    void deleteFile(String path);

}
