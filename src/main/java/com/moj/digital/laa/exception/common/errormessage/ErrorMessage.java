package com.moj.digital.laa.exception.common.errormessage;

public enum ErrorMessage {

    PERSON_NOT_FOUND("No person found in the system with the given identifier %s"),
    PERSON_PERSIST_ERROR("Error whilst persisting person information");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
