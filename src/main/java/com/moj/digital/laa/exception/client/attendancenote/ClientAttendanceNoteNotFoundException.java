package com.moj.digital.laa.exception.client.attendancenote;

import com.moj.digital.laa.exception.client.common.EntityNotFoundException;

public class ClientAttendanceNoteNotFoundException extends EntityNotFoundException {
    public ClientAttendanceNoteNotFoundException(String message) {
        super(message);
    }
}
