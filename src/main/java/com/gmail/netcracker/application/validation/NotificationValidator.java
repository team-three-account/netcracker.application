package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gmail.netcracker.application.utilites.Utilities.compareDates;
import static com.gmail.netcracker.application.utilites.Utilities.isBeforeCurrentDate;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class NotificationValidator extends ModelValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User userNotificationOptions = (User) o;
        rejectIfEmptyOrWhitespace(errors, "notificationPeriodicity", "required.field");
        rejectIfEmptyOrWhitespace(errors, "notificationStartDate", "required.field");
        rejectIfEmptyOrWhitespace(errors, "notificationEndDate", "required.field");
        if (!errors.hasErrors()) {
            if (isBeforeCurrentDate(userNotificationOptions.getNotificationStartDate())) {
                errors.rejectValue("notificationStartDate", "required.currentDate");
            }
            if (compareDates(userNotificationOptions.getNotificationStartDate(),
                    userNotificationOptions.getNotificationEndDate())) {
                errors.rejectValue("notificationEndDate", "required.date");
            }
        }
    }
}
