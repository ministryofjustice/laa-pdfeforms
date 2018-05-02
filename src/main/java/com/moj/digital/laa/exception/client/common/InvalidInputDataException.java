package com.moj.digital.laa.exception.client.common;

public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
