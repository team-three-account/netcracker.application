package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource(value = "classpath:message_en.properties")
public abstract class ModelValidator {
    @Value("${pattern.english.letters.numbers}")
    private String patternEnglishLettersAndNumbers;

    @Value("${pattern.english.letters}")
    private String patternOnlyEnglishLetters;

    @Value("${pattern.english.symbols}")
    private String patternWithSymbols;

    private Logger logger = Logger.getLogger(ModelValidator.class.getName());

    protected void validateEntity(Event event, Errors errors) {
        if (event.getDraft().equals(true)) {
            validateEntityName(event.getName(), errors);

        } else {
            logger.info(event.getDescription());
            validateEntityNameAndDescription(event.getName(), event.getDescription(), errors);
        }
    }

    protected void validateEntity(User user, Errors errors, Boolean resetPass) {
        if (true == resetPass) {
            Pattern pattern = Pattern.compile(patternEnglishLettersAndNumbers);
            Matcher matcherPassword = pattern.matcher(user.getPassword());
            Matcher matcherConfirmPassword = pattern.matcher(user.getConfirmPassword());
            Boolean validPassword = matcherPassword.matches();
            Boolean validConfirmPassword = matcherConfirmPassword.matches();
            if (validPassword.equals(false)) {
                errors.rejectValue("password", "pattern.error");
            }
            if (validConfirmPassword.equals(false)) {
                errors.rejectValue("confirmPassword", "pattern.error");
            }
        } else {
            Pattern pattern = Pattern.compile(patternOnlyEnglishLetters);
            Matcher matcherName = pattern.matcher(user.getName());
            Matcher matcherSurname = pattern.matcher(user.getSurname());
            Boolean validName = matcherName.matches();
            Boolean validSurname = matcherSurname.matches();
            if (validName.equals(false)) {
                errors.rejectValue("name", "pattern.error");
            }
            if (validSurname.equals(false)) {
                errors.rejectValue("surname", "pattern.error");
            }
        }
    }

    protected void validateEntity(Note note, Errors errors) {
        validateEntityNameAndDescription(note.getName(), note.getDescription(), errors);
    }

    protected void validateEntity(Folder folder, Errors errors) {
        validateEntityName(folder.getName(), errors);
    }

    protected void validateEntity(Item item, Errors errors) {
        validateEntityNameAndDescription(item.getName(), item.getDescription(), errors);

    }

    private void validateEntityNameAndDescription(String name, String description, Errors errors) {
        Pattern patternName = Pattern.compile(patternEnglishLettersAndNumbers);
        Pattern patternDescription = Pattern.compile(patternWithSymbols);
        Matcher matcherName = patternName.matcher(name);
        Matcher matcherDescription = patternDescription.matcher(description);
        Boolean validName = matcherName.matches();
        Boolean validDescription = matcherDescription.matches();
        if (validName.equals(false)) {
            errors.rejectValue("name", "pattern.error");
        }
        if (validDescription.equals(false)) {
            errors.rejectValue("description", "pattern.error");
        }
    }

    private void validateEntityName(String name, Errors errors) {
        Pattern pattern = Pattern.compile(patternEnglishLettersAndNumbers);
        Matcher matcherName = pattern.matcher(name);
        Boolean validName = matcherName.matches();
        if (validName.equals(false)) {
            errors.rejectValue("name", "pattern.error");
        }
    }
}
