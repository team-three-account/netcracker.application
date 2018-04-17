package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@PropertySource(value = "classpath:message_en.properties")
public class RegisterValidator implements Validator {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword","required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"birthdayDate","required.field");
        if( !user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","match.password");
        }
        if (userService.findUserByEmail(user.getEmail()).getEmail() != null) {
            errors.rejectValue("email", "email.already.exist");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.field");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


}
