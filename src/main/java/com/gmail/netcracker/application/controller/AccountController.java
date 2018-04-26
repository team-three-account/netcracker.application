package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private User user;

    private EmailConcructor emailConcructor;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private ResetConfirmPasswordValidator resetConfirmPasswordValidator;

    @Autowired
    public AccountController(User user, EmailConcructor emailConcructor, UserService userService, PasswordEncoder passwordEncoder, ResetConfirmPasswordValidator resetConfirmPasswordValidator) {
        this.user = user;
        this.emailConcructor = emailConcructor;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.resetConfirmPasswordValidator = resetConfirmPasswordValidator;
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        model.addAttribute("auth_user", userService.findUserById(userService.getAuthenticatedUser().getId()));
        return "account/profile";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settings(Model model) {
        model.addAttribute("auth_user", userService.findUserById(userService.getAuthenticatedUser().getId()));
        return "account/settings";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String saveSettings(@ModelAttribute("auth_user") User user,
                               BindingResult result,
                               Model model) {
        user.setId(userService.getAuthenticatedUser().getId());
        if (result.hasErrors()) {
            return settings(model);
        }
        userService.updateUser(user);
        return "redirect:/account/profile";
    }
}
