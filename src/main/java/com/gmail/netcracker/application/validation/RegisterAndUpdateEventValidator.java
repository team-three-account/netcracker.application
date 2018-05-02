package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Timestamp;


@PropertySource(value = "classpath:message_en.properties")
public class RegisterAndUpdateEventValidator implements Validator {

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateStart", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateEnd", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventPlaceName", "required.field");
        if (compareDate(event)) {
            errors.rejectValue("dateEnd", "required.date");
        }
    }

    private boolean compareDate(Event event) {
        boolean status = false;
        Timestamp startTime = Utilites.parseTime(event.getDateStart());
        Timestamp endTime = Utilites.parseTime(event.getDateEnd());
        if (endTime.before(startTime)) {
            status = true;
        }
        return status;
    }
}
