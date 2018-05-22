package com.gmail.netcracker.application.service.imp;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.gmail.netcracker.application.dto.dao.interfaces.PhotoDao;
import com.gmail.netcracker.application.service.interfaces.PhotoService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Locale;


@Service
@Data
@PropertySource("classpath:image.properties")
public class PhotoServiceImp implements PhotoService, Serializable {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserService userService;

    @Value("${image.default.male}")
    private String defaultImageMale;

    @Value("${image.default.female}")
    private String defaultImageFemale;

    @Value("${image.default.event}")
    private String defaultImageForEvents;

    @Value("${image.default.item}")
    private String defaultImageForItems;


    @Value("${image.type.png}")
    private String imageTypePng;


    @Value("${image.type.jpeg}")
    private String imageTypeJpeg;


    @Value("${image.type.jpg}")
    private String imageTypeJpg;


    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    @Value("${dropbox.app.sessionstore.key}")
    private String sessionStoreKey;

    @Value("${app.name}")
    private String appName;

    @Value("${app.base.url}")
    private String appBaseURL;

    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PhotoServiceImp.class.getName());

    @Override
    public String uploadFileOnDropBox(MultipartFile file, String name) throws IOException, DbxException {
        DbxRequestConfig config = new DbxRequestConfig(appName, Locale.getDefault().toString());
        DbxClientV2 client = new DbxClientV2(config, sessionStoreKey);
        InputStream inputStream = file.getInputStream();
        String link = null;
        try {
            FileMetadata fileMetadata = client.files().uploadBuilder("/" + name + ".jpg").uploadAndFinish(inputStream);
            String url = client.sharing().createSharedLinkWithSettings("/" + name + ".jpg", SharedLinkSettings.newBuilder().build()).getUrl();
            String id = fileMetadata.getId();
            String fileName = fileMetadata.getName();
            String url2 = url.substring(26, url.length());
            link = "https://dl.dropboxusercontent.com/s/" + url2;
            logger.info(url2);
            logger.info(link);
        } catch (Exception e) {

            throw e;
        } finally {
            inputStream.close();
        }

        return link;
    }

    @Override
    public void deleteFile(String path) {

        DbxRequestConfig config = new DbxRequestConfig(appName, Locale.getDefault().toString());
        DbxClientV2 client = new DbxClientV2(config, sessionStoreKey);
        try {
            Metadata metadata = client.files().delete("/"+ path.substring(52, 92));
        } catch (DbxException dbxe) {
            dbxe.printStackTrace();
        }
    }


}