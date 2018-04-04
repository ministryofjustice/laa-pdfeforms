package com.moj.digital.laa.controller.person.exceptionhandler;

import com.moj.digital.laa.exception.ErrorDetails;
import com.moj.digital.laa.exception.InvalidPersonDataException;
import com.moj.digital.laa.exception.PersonNotFoundException;
import com.moj.digital.laa.exception.util.FieldsErrorExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class PersonControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private FieldsErrorExtractor fieldsErrorExtractor;

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {


        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Input validation failed",
                fieldsErrorExtractor.extract(ex.getBindingResult()));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


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
