package com.gmail.netcracker.application.controller.other;

import com.gmail.netcracker.application.exception.BadRequestException;
import com.gmail.netcracker.application.exception.ForbiddenException;
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
public class ExceptionController {

    private final static String NO_HANDLER_FOUND_EXCEPTION_MESSAGE
            = "Error 404. Not found url!";

    /**
     * Message exception BadRequestException.
     */
    private final static String BAD_REQUEST_EXCEPTION_MESSAGE
            = "Error in query!";


    /**
     * Message exception ForbriddenException.
     */
    private final static String FORBIDDEN_EXCEPTION_MESSAGE
            = "You do not have permissions to access this page.\n.";


    /**
     * Message another exception.
     */
    private final static String OTHER_EXCEPTION_MESSAGE
            = "Temporary problems with the server ... We apologize!";


    /**
     * Handling NoHandlerFoundException
     * exception (http status 404).
     *
     * @return {@link ModelAndView}.
     */

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

    /**
     * Handling NoHandlerFoundException
     * exception (http status 400).
     *
     * @return {@link ModelAndView}.
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView badRequestException(
            final BadRequestException exception,
            final HttpServletRequest request
    ) {
        return handleException(
                exception,
                request,
                BAD_REQUEST_EXCEPTION_MESSAGE
        );
    }

    /**
     * Handling NoHandlerFoundException
     * exception (http status 403).
     *
     * @return {@link ModelAndView}.
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView forbiddenException(
            final ForbiddenException exception,
            final HttpServletRequest request
    ) {
        return handleException(
                exception,
                request,
                FORBIDDEN_EXCEPTION_MESSAGE
        );
    }

    /**
     * Handling NoHandlerFoundException
     * exception (http status 500 and ect).
     *
     * @return {@link ModelAndView}.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView otherException(
            final Exception exception,
            final HttpServletRequest request) {
        return handleException(
                exception,
                request,
                OTHER_EXCEPTION_MESSAGE
        );
    }

    /**
     * Handling NoHandlerFoundException
     * exception constructor.
     *
     * @return {@link ModelAndView}.
     */
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
