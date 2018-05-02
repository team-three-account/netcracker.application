package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private VerificationToken verificationToken;

    private User user;

    private final RegisterValidator registerValidator;

    private final EmailConcructor emailConcructor;

    private final UserService userService;

    @Autowired
    public RegistrationController(VerificationToken verificationToken, User user, RegisterValidator registerValidator, EmailConcructor emailConcructor, UserService userService) {
        this.verificationToken = verificationToken;
        this.user = user;
        this.registerValidator = registerValidator;
        this.emailConcructor = emailConcructor;
        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "user/registration/login";
    }


    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String newAccount(Model model) {
        model.addAttribute("registrationForm", new User());
        return "user/registration/registration";
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public String registerUserAccount(
            @ModelAttribute("registrationForm") User user, BindingResult bindingResult) {
        registerValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/registration/registration";
        }
        emailConcructor.registerEmailSender(user);
        return "user/registration/approve";
    }

    @RequestMapping(value = "/user/registration/approve", method = RequestMethod.GET)
    public String approve(Model model) {
        return "user/registration/approve";
    }


    @RequestMapping(value = "/registrationConfirm/{token}", method = RequestMethod.GET)
    public String confirmRegistration
            (@PathVariable(value = "token") String token) {
        verificationToken = userService.getVerificationToken(token);
        user = verificationToken.getUser();
        user.setPhoto("1");
        userService.saveRegisteredUser(user);
        userService.deleteVerificationToken(verificationToken);
        return "user/registration/successfulRegistration";
    }
}
