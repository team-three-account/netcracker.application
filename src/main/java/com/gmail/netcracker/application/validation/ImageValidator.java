package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ImageValidator {

    @Autowired
    private PhotoServiceImp photoService;

    public Boolean validateImageFormat(ModelAndView modelAndView, MultipartFile imageFile) {

        if (!imageFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !imageFile.getContentType().equals(photoService.getImageTypeJpg())
                && !imageFile.getContentType().equals(photoService.getImageTypePng())
                && !imageFile.isEmpty()) {
            modelAndView.addObject("message", "Image type don't supported");
            return false;
        }
        return true;
    }


}
