package com.gmail.netcracker.application.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoService {


    void remove(Long id);

    void saveFileInFileSystem(MultipartFile photo,String name);

    void saveFileInDB(String fileName, Long id);

    void deleteFile(String url);

}
