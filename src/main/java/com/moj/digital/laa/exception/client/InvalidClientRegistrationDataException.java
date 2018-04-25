package com.moj.digital.laa.exception.client;

public class InvalidClientRegistrationDataException extends RuntimeException {
    public InvalidClientRegistrationDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
