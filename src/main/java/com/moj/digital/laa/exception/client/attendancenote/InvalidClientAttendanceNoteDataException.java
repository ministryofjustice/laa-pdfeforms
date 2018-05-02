package com.moj.digital.laa.exception.client.attendancenote;

import com.moj.digital.laa.exception.client.common.InvalidInputDataException;

public class InvalidClientAttendanceNoteDataException extends InvalidInputDataException {
    public InvalidClientAttendanceNoteDataException(String message, Throwable exception) {
        super(message, exception);
    }
}
