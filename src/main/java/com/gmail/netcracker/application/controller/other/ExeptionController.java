package com.gmail.netcracker.application.controller.other;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ComponentScan(basePackages = "com.gmail.netcraker.application.service*")
public class ExeptionController {

    /**
     * Сообщение исключения NoHandlerFoundException.
     */
    private final static String NO_HANDLER_FOUND_EXCEPTION_MESSAGE
            = "Ошибка 404. Не найдено!";

    /**
     * Сообщение исключения BadRequestException.
     */
    private final static String BAD_REQUEST_EXCEPTION_MESSAGE
            = "Ошибка в запросе!";

    /**
     * Сообщение исключения WrongInformationException.
     */
    private final static String WRONG_INFORMATION_EXCEPTION_MESSAGE
            = "Ошибка в запросе!";

    /**
     * Сообщение исключения ForbriddenException.
     */
    private final static String FORBIDDEN_EXCEPTION_MESSAGE
            = "У Вас нет достаточных прав для доступа к этой странице.";

    /**
     * Сообщение исключения DuplicateException.
     */
    private final static String DUPLICATE_EXCEPTION_MESSAGE
            = "Такой объект уже существует!";

    /**
     * Сообщение все других исключений.
     */
    private final static String OTHER_EXCEPTION_MESSAGE
            = "Временные неполадки с сервером... Приносим свои извинения!";


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView noHandlerFoundException(
            final NoHandlerFoundException exception,
            final HttpServletRequest request
    ) {
        return handleException(
                exception,
                request,
                NO_HANDLER_FOUND_EXCEPTION_MESSAGE
        );
    }


    private ModelAndView handleException(
            final Exception ex,
            final HttpServletRequest request,
            final String textError
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("Request", request);
        modelAndView.addObject("text_error", textError);
        modelAndView.addObject(
                "message_error",
                ex.getClass()
                        .getSimpleName()
                        + " : "
                        + ex.getMessage()
        );
        modelAndView.setViewName("error");
        return modelAndView;
    }


}
