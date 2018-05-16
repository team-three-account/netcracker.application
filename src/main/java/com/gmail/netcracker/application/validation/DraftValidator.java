package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.dto.model.Item;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@PropertySource(value = "classpath:message_en.properties")
public class DraftValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventPlaceName", "required.field");
    }
}
