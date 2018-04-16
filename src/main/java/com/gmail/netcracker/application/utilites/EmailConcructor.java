package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.User;
import org.springframework.mail.SimpleMailMessage;

public class EmailConcructor {

    public static SimpleMailMessage constructRegisterEmailMessage(final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = "http://localhost:8000/registrationConfirm/" + token;
        final String message = "Reg sucss";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("netcracker.application@gmail.com");
        return email;
    }


    public static SimpleMailMessage constructPasswordResetEmailMessage(final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password";
        final String confirmationUrl = "http://localhost:8000/account/changePassword/" + token;
        final String message = "Link for reset password for user " + user.getEmail();
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("netcracker.application@gmail.com");
        return email;
    }
}
