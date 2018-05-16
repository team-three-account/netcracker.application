package com.gmail.netcracker.application.controller;


import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private User user;

    private EmailConstructor emailConstructor;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private ResetConfirmPasswordValidator resetConfirmPasswordValidator;

    private PhotoServiceImp photoService;

    @Autowired
    public AccountController(User user, EmailConstructor emailConstructor, UserService userService, PasswordEncoder passwordEncoder, ResetConfirmPasswordValidator resetConfirmPasswordValidator, PhotoServiceImp photoService) {
        this.user = user;
        this.emailConstructor = emailConstructor;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.resetConfirmPasswordValidator = resetConfirmPasswordValidator;
        this.photoService = photoService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView homeAccount(ModelAndView model) {
        model.addObject("auth_user", userService.getAuthenticatedUser());
        model.setViewName("account/account");
        return model;
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public String passwordResetSuccessful() {

        emailConstructor.resetPasswordEmailSender(userService.getAuthenticatedUser());
        return "account/passwordResetSuccessful";
    }

    @RequestMapping(value = {"/changePassword/{token}"}, method = RequestMethod.GET)
    public String newPassword(Model model, @PathVariable(value = "token") String token) {
        model.addAttribute("veriftoken", userService.getVerificationToken(token));
        model.addAttribute("user", new User());

        return "account/changePassword";
    }


    @RequestMapping(value = "/changePassword/{token}", method = RequestMethod.POST)
    public String changePassword(@RequestParam(value = "password") String password,
                                 @RequestParam(value = "confirmPassword") String confirmPassword,
                                 @ModelAttribute(value = "veriftoken") VerificationToken verificationToken,
                                 @ModelAttribute("user") User user,
                                 @PathVariable("token") String token,
                                 BindingResult bindingResult, Model model) {

        user.setConfirmPassword(confirmPassword);
        verificationToken = userService.getVerificationToken(token);
        user.setEmail(verificationToken.getUser().getEmail());
        resetConfirmPasswordValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("veriftoken", verificationToken);
            return "account/changePassword";
        }
        verificationToken.getUser().setPassword(passwordEncoder.encode(password));
        userService.changeUserPassword(verificationToken.getUser().getPassword(), verificationToken.getUser().getEmail());
        userService.deleteVerificationToken(verificationToken);
        return "account/successfulChange";
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String profile(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("auth_user", userService.findUserById(id));
        return "account/profile";
    }

    @RequestMapping(value = "/settings-user/{id}", method = RequestMethod.GET)
    public ModelAndView settings(@PathVariable(value = "id") Long id, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.findUserById(id));
        modelAndView.setViewName("account/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/settings-user", method = RequestMethod.POST)
    public ModelAndView saveSettings(@ModelAttribute(value = "auth_user") User user,
                                     @RequestParam(value = "photo") String photo,
                                     @RequestParam(value = "photoFile") final MultipartFile photoFile,
                                     final ModelAndView modelAndView,
                                     HttpServletRequest httpServletRequest) throws Exception {

        user.setPhotoFile(photoFile);
        if (!photoFile.getContentType().equals(photoService.getImageTypeJpeg())
                && !photoFile.getContentType().equals(photoService.getImageTypeJpg())
                && !photoFile.getContentType().equals(photoService.getImageTypePng())
                && !photoFile.isEmpty()) {
            modelAndView.addObject("message", "Image type don't supported");
            modelAndView.setViewName("account/edit");
            return modelAndView;
        }
        userService.getAuthenticatedUser().setPhoto(user.getPhoto());
        if (photoFile.isEmpty()) {
            user.setPhoto(photo);
        } else {
            user.setPhoto(photoService.uploadFileOnDropBox(photoFile, UUID.randomUUID().toString()));
            userService.getAuthenticatedUser().setPhoto(user.getPhoto());
        }
        userService.getAuthenticatedUser().setName(user.getName());
        userService.getAuthenticatedUser().setSurname(user.getSurname());
        userService.getAuthenticatedUser().setBirthdayDate(user.getBirthdayDate());
        Logger.getLogger(AccountController.class.getName()).info(user.toString());
        userService.updateUser(user);
        modelAndView.setViewName("redirect:/account/profile/" + user.getId());
        return modelAndView;
    }


}
