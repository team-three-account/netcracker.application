package com.gmail.netcracker.application.utilites;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.EventRangeService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@PropertySource("classpath:application.properties")
@Component
@Transactional
public class EmailConstructor {

    @Autowired
    private UserService userService;

    @Autowired
    private EventRangeService eventRangeService;

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PdfConstructor pdfConstructor;

    private Logger logger = Logger.getLogger(EmailConstructor.class.getName());

    private SimpleMailMessage constructRegisterEmailMessage(final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = env.getProperty("heroku.host") + "/registrationConfirm/" + token;
        final String message = "Registration successful!";
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
        final String message = "Link for reset password for you " + user.getEmail();
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

    /**
     * Send e-mail notification about future events from period specified by user
     * @param fromDate specify start of period of selection
     * @param tillDate specify end of period of selection
     * @param user specify user that will send e-mail notification
     */
    public void notifyAboutPersonPlan(Timestamp fromDate, Timestamp tillDate, User user) {
        final List<Event> planedEvents = eventRangeService.getEventsFromRange(user.getId(), fromDate, tillDate);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            constructPersonPlanEmailMessage(message, planedEvents, user);
            javaMailSender.send(message);
        }
        catch (MessagingException e){
            logger.warning("Can not construct email with PDF");
            javaMailSender.send(constructApologizeMessage(user.getEmail()));
        }
    }

    /**
     * Construct message about person plan with attached PDF file
     * @param message construct message with MimeMessageHelper
     * @param planedEvents events to notify about
     * @param user specify user that will send e-mail notification
     * @throws MessagingException
     */
    private void constructPersonPlanEmailMessage(MimeMessage message, List<Event> planedEvents, User user) throws MessagingException {

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        final String recipientAddress = user.getEmail();
        final String subject = "Notification about personal plan";
        final String text;
        if(!planedEvents.isEmpty()){
            text = "We want to remind you of the upcoming events for which you have subscribed. \r\n See PDF for more details: ";
            helper.addAttachment("Personal plan.pdf", pdfConstructor.construct(planedEvents));
            logger.info("Email with PDF is ready to be sent");
        }
        else  text = "You have not any event for the near future. Fill free :) ";

        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom(env.getProperty("server.email"));
    }

    /**
     * Create message of apologize which will be sent to user in case of error during constructing email with person plan.
     * @param emailRecipient email address of user which will receive an email
     * @return SimpleMailMessage
     */
        private SimpleMailMessage constructApologizeMessage(String emailRecipient){
            final String recipientAddress = emailRecipient;
            final String subject = "Notification about personal plan";
            final String message = "We apologize for the temporary problem with person plan notification. Service will be reestablished soon.";
            final SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText(message);
            email.setFrom(env.getProperty("email.server"));
            return email;
        }
}
