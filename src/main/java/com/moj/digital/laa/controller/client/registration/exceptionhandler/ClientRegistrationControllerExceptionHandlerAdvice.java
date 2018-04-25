package com.moj.digital.laa.controller.client.registration.exceptionhandler;

import com.moj.digital.laa.exception.common.ErrorDetails;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.exception.client.InvalidClientRegistrationDataException;
import com.moj.digital.laa.exception.client.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
public class ClientRegistrationControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private FieldsErrorExtractor fieldsErrorExtractor;

    public ClientRegistrationControllerExceptionHandlerAdvice(FieldsErrorExtractor fieldsErrorExtractor) {
        this.fieldsErrorExtractor = fieldsErrorExtractor;
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {


        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Input validation failed",
                fieldsErrorExtractor.extract(ex.getBindingResult()));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = InvalidClientRegistrationDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDetails handleInvalidClientDataException(InvalidClientRegistrationDataException ivpe) {
        log.debug("handleInvalidClientDataException invoked ");
        return new ErrorDetails(LocalDateTime.now(), ivpe.getMessage(), ivpe.getCause().getMessage());
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDetails handleClientNotFoundException(ClientNotFoundException pnfe) {
        log.debug("handleClientNotFoundException invoked ");
        return new ErrorDetails(LocalDateTime.now(), pnfe.getMessage());
    }

}
