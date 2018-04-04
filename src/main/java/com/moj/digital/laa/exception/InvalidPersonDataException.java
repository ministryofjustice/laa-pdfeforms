package com.moj.digital.laa.exception;

public class InvalidPersonDataException extends RuntimeException {
    public InvalidPersonDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
