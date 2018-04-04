package com.moj.digital.laa.controller.advice;

import com.moj.digital.laa.exception.ErrorDetails;
import com.moj.digital.laa.exception.InvalidPersonDataException;
import com.moj.digital.laa.exception.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class PersonControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidPersonDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDetails handleInvalidPersonDataException(InvalidPersonDataException ivpe) {
        log.debug("handleInvalidPersonDataException invoked ");
        return new ErrorDetails(LocalDateTime.now(), ivpe.getMessage(), ivpe.getCause().getMessage());
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    @ResponseBody
    public ErrorDetails handlePersonNotFoundException(PersonNotFoundException pnfe) {
        log.debug("handleInvalidPersonDataException invoked ");
        return new ErrorDetails(LocalDateTime.now(), pnfe.getMessage(), pnfe.getCause().getMessage());
    }

}
