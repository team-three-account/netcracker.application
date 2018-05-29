package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.imp.PhotoServiceImp;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Class controller for register pages.
 */

@Controller
public class RegistrationController {

    @Autowired
    private VerificationToken verificationToken;

    @Autowired
    private User user;

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private EmailConstructor emailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoServiceImp photoService;

    private final String MALE = "Male";

    private final String FEMALE = "Female";

    /**
     * This method returns login page .
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView, String error, String logout) {

        if (error != null) {
            modelAndView.addObject("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            modelAndView.addObject("message", "Logged out successfully.");
        }
        modelAndView.setViewName("user/registration/login");
        return modelAndView;
    }

    /**
     * This method returns registration page .
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public ModelAndView newAccount(ModelAndView modelAndView) {
        modelAndView.addObject("registrationForm", new User());
        modelAndView.setViewName("user/registration/registration");
        return modelAndView;
    }

    /**
     * This method send registration information for new user on server .
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */
    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("registrationForm") User user,
            BindingResult bindingResult,
            ModelAndView modelAndView) {
        registerValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/registration/registration");
            return modelAndView;
        }
        emailConstructor.registerEmailSender(user);
        modelAndView.setViewName("user/registration/approve");
        return modelAndView;
    }

    /**
     * This method return after successful send user information
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */
    @RequestMapping(value = "/user/registration/approve", method = RequestMethod.GET)
    public ModelAndView approve(ModelAndView modelAndView) {
        modelAndView.setViewName("user/registration/approve");
        return modelAndView;
    }

    /**
     * This method return after successful activate account
     *
     * @param modelAndView modelAndView Object class {@link ModelAndView}
     * @return modelAndView
     */
    @RequestMapping(value = "/registrationConfirm/{token}", method = RequestMethod.GET)
    public ModelAndView confirmRegistration
            (@PathVariable(value = "token") String token,
             ModelAndView modelAndView) {
        verificationToken = userService.getVerificationToken(token);
        user = verificationToken.getUser();
        if (user.getGender().equals(MALE)) {
            user.setPhoto(photoService.getDefaultImageMale());
        }
        if (user.getGender().equals(FEMALE)) {
            user.setPhoto(photoService.getDefaultImageFemale());
        }
        userService.saveRegisteredUser(user);
        userService.deleteVerificationToken(verificationToken);
        modelAndView.setViewName("user/registration/successfulRegistration");
        return modelAndView;
    }
}
