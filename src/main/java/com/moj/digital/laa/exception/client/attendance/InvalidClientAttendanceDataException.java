package com.moj.digital.laa.exception.client.attendance;

import com.moj.digital.laa.exception.client.common.InvalidInputDataException;

public class InvalidClientAttendanceDataException extends InvalidInputDataException {
    public InvalidClientAttendanceDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
