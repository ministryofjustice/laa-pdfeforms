package com.moj.digital.laa.model.person;

import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseWrapper {
    private final String responseMessage;

    public ResponseWrapper(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
