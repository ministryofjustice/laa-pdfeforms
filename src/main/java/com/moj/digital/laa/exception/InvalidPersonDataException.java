package com.moj.digital.laa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidPersonDataException extends RuntimeException {
    public InvalidPersonDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
