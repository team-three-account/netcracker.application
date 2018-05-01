package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.PhotoService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.*;
import java.util.logging.Logger;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private User user;

    private EmailConcructor emailConcructor;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private ResetConfirmPasswordValidator resetConfirmPasswordValidator;

    private PhotoService photoService;

    @Autowired
    public AccountController(User user, EmailConcructor emailConcructor, UserService userService, PasswordEncoder passwordEncoder, ResetConfirmPasswordValidator resetConfirmPasswordValidator, PhotoService photoService) {
        this.user = user;
        this.emailConcructor = emailConcructor;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.resetConfirmPasswordValidator = resetConfirmPasswordValidator;
        this.photoService = photoService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String homeAccount(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "account/account";
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public String passwordResetSuccessful() {
        user = userService.getAuthenticatedUser();
        emailConcructor.resetPasswordEmailSender(user);
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
        User user = userService.findUserById(id);

        model.addAttribute("auth_user", userService.findUserById(id));
        return "account/profile";
    }

    @RequestMapping(value = "/settings-user/{id}", method = RequestMethod.GET)
    public ModelAndView settings(@PathVariable(value = "id") Long id, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.findUserById(id));
        Logger.getLogger(AccountController.class.getName()).info(userService.findUserById(id).toString());
        modelAndView.setViewName("account/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSettings(@RequestParam(value = "id") final Long id,
                              @RequestParam(value = "name") final String name,
                              @RequestParam(value = "surname") final String surname,
                              @RequestParam(value = "phone") final String phone,
                              @RequestParam(value = "photoFile") final MultipartFile photoFile,
                              final ModelAndView modelAndView) {
        User user = userService.findUserById(id);
        Logger.getLogger(AccountController.class.getName()).info(user.toString());
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setPhotoFile(photoFile);
        Logger.getLogger(AccountController.class.getName()).info(user.toString());
        user.setPhoto(String.valueOf(System.currentTimeMillis()));
        userService.getAuthenticatedUser().setPhoto(user.getPhoto());
        photoService.saveFileInFileSystem(user.getPhotoFile(), user.getPhoto());
        photoService.saveFileInDB(user.getPhoto(), user.getId());
        userService.updateUser(user);
        Logger.getLogger(AccountController.class.getName()).info(user.toString());
        modelAndView.setViewName("redirect:/account/profile/" + id);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
    public byte[] testphoto(@PathVariable(value = "id") Long id) throws IOException {
        InputStream inputStream = new FileInputStream(new File( PhotoServiceImp.PATH+ id+".jpg" ));
        return IOUtils.toByteArray(inputStream);
    }
}
