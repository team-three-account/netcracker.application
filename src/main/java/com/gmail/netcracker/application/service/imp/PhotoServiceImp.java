package com.gmail.netcracker.application.service.imp;

import com.gmail.netcracker.application.dto.dao.interfaces.PhotoDao;
import com.gmail.netcracker.application.service.interfaces.PhotoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
@Data
@PropertySource("classpath:image.properties")
public class PhotoServiceImp implements PhotoService, Serializable {

    @Autowired
    private PhotoDao photoDao;

    public static final String PATH = "/app/";

    @Value("${image.default}")
    private String defaultImage;

    @Value("${image.type.png}")
    private String imageTypePng;


    @Value("${image.type.jpeg}")
    private String imageTypeJpeg;


    @Value("${image.type.jpg}")
    private String imageTypeJpg;


    @Override
    public void remove(Long id) {

    }

    @Override
    public void saveFileInFileSystem(MultipartFile photo, String name) {
        if (photo != null && !photo.isEmpty()) {
            try (
                    OutputStream stream = new FileOutputStream(PhotoServiceImp.PATH +
                            name + ".jpg"
                    )

            ) {
                stream.write(
                        photo.getBytes()
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveFileInDB(String fileName, Long id) {
        photoDao.insert(fileName, id);
    }

    @Override
    public void deleteFile(String url) {

        final File file = new File(PhotoServiceImp.PATH + url);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

}
