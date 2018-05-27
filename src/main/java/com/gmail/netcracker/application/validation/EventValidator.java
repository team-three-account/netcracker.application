package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gmail.netcracker.application.utilites.Utilities.compareDates;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class EventValidator extends ModelValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;
        rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        rejectIfEmptyOrWhitespace(errors, "description", "required.field");
        rejectIfEmptyOrWhitespace(errors, "dateStart", "required.field");
        rejectIfEmptyOrWhitespace(errors, "dateEnd", "required.field");
        rejectIfEmptyOrWhitespace(errors, "type", "required.field");
        rejectIfEmptyOrWhitespace(errors, "eventPlaceName", "required.field");
        if (!errors.hasErrors()) {
            if (compareDates(event.getDateStart(), event.getDateEnd())) {
                errors.rejectValue("dateEnd", "required.date");
            }
            validateEntity(event, errors);
        }
    }
}
