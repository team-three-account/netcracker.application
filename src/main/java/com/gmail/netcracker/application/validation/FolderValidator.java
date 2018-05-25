package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Folder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class FolderValidator extends ModelValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Folder.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Folder folder = (Folder) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field");
        validateEntity(folder,errors);
    }
}
