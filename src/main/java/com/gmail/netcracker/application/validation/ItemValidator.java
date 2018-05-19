package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.utilites.Utilities;
import org.apache.commons.validator.routines.UrlValidator;
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
    if (validationLink(item)) {
      errors.rejectValue("link", "required.link");
    }
    if (expectedDate(item)) {
      errors.rejectValue("dueDate", "required.currentDate");
    }
  }

  private boolean expectedDate(Item item) {
    boolean status = false;
    Timestamp currentDate = Utilities.getCurrentTimeStamp();
    Timestamp setDate = Utilities.parseStringToTimestampWithoutHours(item.getDueDate());
    if (setDate != null) {
      if (setDate.before(currentDate)) {
        status = true;
      }
    }
    return status;
  }

  public boolean validationLink(Item item) {
    boolean status = true;
    String[] schemes = {"http", "https"};
    if (!item.getLink().isEmpty()) {
      UrlValidator urlValidator = new UrlValidator(schemes);
      if (urlValidator.isValid(item.getLink())) {
        status = false;
      }
    }
    return status;
  }
}


