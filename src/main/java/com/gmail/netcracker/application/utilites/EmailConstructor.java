package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PropertySource("classpath:application.properties")
@Component
public class EmailConstructor {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    Environment env;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    PdfConstructor pdfConstructor;

    private SimpleMailMessage constructRegisterEmailMessage(final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = env.getProperty("heroku.host") + "/registrationConfirm/" + token;
        final String message = "Reg sucss";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("email.server"));
        return email;
    }


    private SimpleMailMessage constructPasswordResetEmailMessage(final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password";
        final String confirmationUrl = env.getProperty("heroku.host") + "/account/changePassword/" + token;
        final String message = "Link for reset password for user " + user.getEmail();
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("email.server"));
        return email;
    }

    private SimpleMailMessage constructNewFriendEmailMessage(final User sender, final Long recipientId) {
        final String recipientAddress = userService.findUserById(recipientId).getEmail();
        final String subject = "Notification about new request";
        final String viewUrl = env.getProperty("heroku.host") + "/account/friends/incoming";
        final String message = "User " + sender.getName() + " " + sender.getSurname() + " want to add you to friends. \r\n View request: ";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + viewUrl);
        email.setFrom(env.getProperty("email.server"));
        return email;
    }

    private SimpleMailMessage createEventNotification(Event event) {
        final String recipientAddress = userService.findUserById(event.getCreator()).getEmail();
        final String subject = "Notification about event";
        final String viewUrl = env.getProperty("heroku.host") + "/account/eventList/event-" + event.getEventId();
        final String message = event.getName() + " is now!!! View event: ";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + viewUrl);
        email.setFrom(env.getProperty("email.server"));
        return email;
    }

    public void registerEmailSender(User user) {
        final String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        final SimpleMailMessage email = constructRegisterEmailMessage(user, token);
        javaMailSender.send(email);
    }

    public void resetPasswordEmailSender(User user) {
        final String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        final SimpleMailMessage email = constructPasswordResetEmailMessage(user, token);
        javaMailSender.send(email);
    }

    public void notifyNewFriendEmailSender(User sender, Long recipientId) {
        final SimpleMailMessage email = constructNewFriendEmailMessage(sender, recipientId);
        javaMailSender.send(email);
    }

    public void notifyAboutEvent(Event event) {
        final SimpleMailMessage email = createEventNotification(event);
        javaMailSender.send(email);
    }

    public void notifyAboutPersonPlan(Timestamp fromDate, Timestamp tillDate, User user) {
        try {
            final List<Event> planedEvents = eventService.getEventsFromRange(fromDate, tillDate, user.getId());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = constructPersonPlanEmailMessage(message, true, planedEvents, user);
            javaMailSender.send(message);
        }
        catch (MessagingException e){
        }
    }

    private MimeMessageHelper constructPersonPlanEmailMessage(MimeMessage message, boolean multipart, List<Event> planedEvents, User user) throws MessagingException {

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        final String recipientAddress = user.getEmail();
        final String subject = "Notification about person plan";
        final String text;
        if(planedEvents.size()>0){
            text = "We want to remind you of the upcoming events for which you have subscribed. \r\n See PDF for more details: ";
            helper.addAttachment("Person plan.pdf", pdfConstructor.construct(planedEvents));
        }
        else  text = "You have not any event for the near future. Fill free :) ";

        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom(env.getProperty("server.email"));

        return helper;
    }
}
