package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.EventSerializer;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.EditUserAccountValidator;
import com.gmail.netcracker.application.validation.ImageValidator;
import com.gmail.netcracker.application.validation.NotificationValidator;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private EmailConstructor emailConstructor;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private ResetConfirmPasswordValidator resetConfirmPasswordValidator;

    private PhotoServiceImp photoService;

    private EditUserAccountValidator editUserAccountValidator;

    private EventService eventService;

    private ItemService itemService;

    private ImageValidator imageValidator;

    private NotificationValidator notificationValidator;

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Event.class, new EventSerializer())
            .create();

    @Autowired
    public AccountController(EmailConstructor emailConstructor,
                             UserService userService,
                             PasswordEncoder passwordEncoder,
                             ResetConfirmPasswordValidator resetConfirmPasswordValidator,
                             PhotoServiceImp photoService,
                             EditUserAccountValidator editUserAccountValidator,
                             EventService eventService,
                             ItemService itemService,
                             ImageValidator imageValidator,
                             NotificationValidator notificationValidator) {
        this.emailConstructor = emailConstructor;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.resetConfirmPasswordValidator = resetConfirmPasswordValidator;
        this.photoService = photoService;
        this.editUserAccountValidator = editUserAccountValidator;
        this.eventService = eventService;
        this.itemService = itemService;
        this.imageValidator = imageValidator;
        this.notificationValidator = notificationValidator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView homeAccount(ModelAndView modelAndView) {
        String eventList = gson.toJson(eventService.myEventsWithPriority());
        modelAndView.addObject("popularItems", itemService.popularItems());
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("eventList", eventList);
        modelAndView.setViewName("account/account");
        return modelAndView;
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
    public ModelAndView profile(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("auth_user", userService.findUserById(id));
        model.setViewName("account/profile");
        return model;
    }

    @RequestMapping(value = "/settings-user/{id}", method = RequestMethod.GET)
    public ModelAndView settings(@PathVariable(value = "id") Long id, ModelAndView modelAndView) {
        User user = userService.findUserById(id);
        modelAndView.addObject("auth_user", user);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("account/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/notificationSettings/{id}", method = RequestMethod.GET)
    public ModelAndView notificationSettings(@PathVariable(value = "id") Long userId,
                                             ModelAndView modelAndView) {
        modelAndView.addObject("userNotificationOptions", userService.findUserById(userId));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("account/notificationSettings");
        return modelAndView;
    }

    @RequestMapping(value = "/notificationSettings/save", method = RequestMethod.POST)
    public ModelAndView saveNotificationSettings(@ModelAttribute("userNotificationOptions") User userNotificationOptions,
                                                 @RequestParam(value = "isNotificationsEnabled", required = false) Boolean isNotificationEnabled,
                                                 BindingResult result, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("account/notificationSettings");
        if (isNotificationEnabled == null) {
            eventService.disableNotifications(userNotificationOptions.getId());
        } else if (isNotificationEnabled.equals(true)) {
            notificationValidator.validate(userNotificationOptions, result);
            if (result.hasErrors()) {
                return modelAndView;
            }
            eventService.updateNotificationSchedule(userNotificationOptions);
        }
        modelAndView.setViewName("redirect:/account/profile/" + userNotificationOptions.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/settings-user", method = RequestMethod.POST)
    public ModelAndView saveSettings(@ModelAttribute(value = "user") User user,
                                     BindingResult result,
                                     @RequestParam(value = "photo") String photo,
                                     @RequestParam(value = "photoFile") MultipartFile photoFile,
                                     ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        Boolean imageFormat = imageValidator.validateImageFormat(modelAndView, photoFile);
        editUserAccountValidator.validate(user, result);
        user.setPhotoFile(photoFile);
        if (result.hasErrors() || imageFormat.equals(false)) {
            modelAndView.setViewName("account/edit");
            return modelAndView;
        }

        userService.getAuthenticatedUser().setPhoto(user.getPhoto());
        if (photoFile.isEmpty()) {
            user.setPhoto(photo);
        } else if (!photo.equals(photoService.getDefaultImageFemale()) || !photo.equals(photoService.getDefaultImageMale())) {
            photoService.deleteFile(user.getPhoto());
            user.setPhoto(photoService.uploadFileOnDropBox(photoFile, UUID.randomUUID().toString()));

        }
        userService.getAuthenticatedUser().setPhoto(user.getPhoto());
        userService.getAuthenticatedUser().setName(user.getName());
        userService.getAuthenticatedUser().setSurname(user.getSurname());
        userService.getAuthenticatedUser().setBirthdayDate(user.getBirthdayDate());
        userService.updateUser(user);
        modelAndView.setViewName("redirect:/account/profile/" + user.getId());
        return modelAndView;
    }

}
