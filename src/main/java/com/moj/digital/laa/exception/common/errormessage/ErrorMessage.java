package com.moj.digital.laa.exception.common.errormessage;

public enum ErrorMessage {

    CLIENT_NOT_FOUND("No client found in the system with the given identifier %s"),
    CLIENT_PERSIST_ERROR("Error whilst persisting client information"),
    ATTENDANCE_NOTE_NOT_FOUND("No attendance note found in the system with the given identifier %s");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
