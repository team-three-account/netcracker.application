package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class ResetConfirmPasswordValidator implements Validator {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword","required.field");
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","match.password");
        }
        if(user.getPassword().equals(userService.findUserByEmail(user.getEmail()).getPassword())){
            errors.rejectValue("confirmPassword","password.actual");
        }
    }
}
