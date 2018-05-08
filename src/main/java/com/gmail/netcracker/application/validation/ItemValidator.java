package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "link", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dueDate", "required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority", "required.field");
    }

    public ModelAndView validateItem(ModelAndView modelAndView, Item item, BindingResult bindingResult, ItemService itemService) {

        if (bindingResult.hasErrors() && item.getDueDate() != null && item.getLink().trim().length() != 0) {
            return modelAndView;
        }
        if (item.getName().trim().length() != 0 && item.getDescription().trim().length() != 0
                && item.getDueDate() == null) {
            itemService.add(item);
            modelAndView.setViewName("redirect:/account/wishList");
            return modelAndView;
        }
        if (item.getName().trim().length() != 0 && item.getDescription().trim().length() != 0
                && item.getDueDate() != null) {
            itemService.add(item);
            modelAndView.setViewName("redirect:/account/wishList");
            return modelAndView;
        }
        return modelAndView;
    }
}
