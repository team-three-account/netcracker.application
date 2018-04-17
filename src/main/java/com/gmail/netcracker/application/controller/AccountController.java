package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EmailConcructor;
import com.gmail.netcracker.application.utilites.VerificationToken;
import com.gmail.netcracker.application.validation.ResetConfirmPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ResetConfirmPasswordValidator resetConfirmPasswordValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String homeAccount(Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        return "account/account";
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public String passwordResetSuccessful() {
        User user = userService.getAuthenticatedUser();
        passwordApprove(user);
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
        resetConfirmPasswordValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("veriftoken", verificationToken);
            return "account/changePassword";
        }
        verificationToken.getUser().setPassword(password);
        userService.changeUserPassword(verificationToken.getUser().getPassword(), verificationToken.getUser().getEmail());
        userService.deleteVerificationToken(verificationToken);
        return "account/successfulChange";
    }

    public VerificationToken passwordApprove(User user) {
        final String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = userService.createVerificationToken(user, token);
        final SimpleMailMessage email = EmailConcructor.constructPasswordResetEmailMessage(user, token);
        javaMailSender.send(email);
        return verificationToken;
    }
}
