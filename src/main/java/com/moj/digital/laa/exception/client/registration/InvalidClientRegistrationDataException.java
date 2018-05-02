package com.moj.digital.laa.exception.client.registration;

import com.moj.digital.laa.exception.client.common.InvalidInputDataException;

public class InvalidClientRegistrationDataException extends InvalidInputDataException {
    public InvalidClientRegistrationDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
