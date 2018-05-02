package com.moj.digital.laa.exception.client.attendance;

import com.moj.digital.laa.exception.client.common.EntityNotFoundException;

public class ClientAttendanceNotFoundException extends EntityNotFoundException {
    public ClientAttendanceNotFoundException(String message) {
        super(message);
    }
}
