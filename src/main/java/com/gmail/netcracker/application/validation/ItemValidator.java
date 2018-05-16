package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.utilites.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Timestamp;

@PropertySource(value = "classpath:message_en.properties")
public class ItemValidator implements Validator {

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Item item = (Item) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required.field");
        if (expectedDate(item)) {
            errors.rejectValue("dueDate", "required.currentDate");
        }
    }

    private boolean expectedDate(Item item) {
        boolean status = false;
        Timestamp currentDate = Utilities.getCurrentTimeStamp();
        Timestamp setDate = Utilities.parseStringToTimestamp(item.getDueDate());
        if (setDate != null) {
            if (setDate.before(currentDate)) {
                status = true;
            }
        }
        return status;
    }
}


