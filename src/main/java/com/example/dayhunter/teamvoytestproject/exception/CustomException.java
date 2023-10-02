package com.example.dayhunter.teamvoytestproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@RestControllerAdvice
@ResponseBody
public class CustomException {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object serverError(final Throwable throwable, WebRequest webRequest) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                throwable.getMessage(),
                webRequest.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFound(final Exception throwable, WebRequest webRequest) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                throwable.getMessage(),
                webRequest.getDescription(false));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public Object handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler({RuntimeException.class})
    public Object handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}
