package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator extends ModelValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required.field");
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "match.password");
        }
        User registeredUser = userService.findUserByEmail(user.getEmail());
        if (registeredUser != null && user.getEmail().equals(registeredUser.getEmail())) {
            errors.rejectValue("email", "email.already.exist");
        }
        validateEntity(user,errors,false);


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


}
