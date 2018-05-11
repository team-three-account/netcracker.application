package com.gmail.netcracker.application.validation;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

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
        Timestamp currentDate = Utilites.getCurrentTimeStamp();
        Timestamp setDate = Utilites.parseTime(item.getDueDate());
        if (setDate != null) {
            if (setDate.before(currentDate)) {
                status = true;
            }
            if (setDate.equals(currentDate)) {
                status = false;
            }
        }
        return status;
    }

    //additional validation for the item

    public ModelAndView validateItem(ModelAndView modelAndView, Item item, BindingResult bindingResult,
                                     ItemService itemService, UserService userService, String methodName) {

        if (!item.getName().isEmpty() && !item.getDescription().isEmpty() && item.getDueDate().isEmpty()) {
            check(methodName, item, itemService, userService, modelAndView);
        }

        if (!item.getName().isEmpty() && !item.getDescription().isEmpty() && !bindingResult.hasErrors()) {
            check(methodName, item, itemService, userService, modelAndView);
        }
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        return modelAndView;
    }


    //check for the method name in which we either add an item or do an update of the item

    public ModelAndView check(String methodName, Item item, ItemService itemService, UserService userService,
                               ModelAndView modelAndView) {
        if (methodName.equals("updateItem")) {
            itemService.update(item);
        } else if (methodName.equals("addItem")) {
            itemService.add(item);
        }
        modelAndView.setViewName("redirect:/account/user-" + userService.getAuthenticatedUser().getId() + "/wishList");
        return modelAndView;
    }
}


