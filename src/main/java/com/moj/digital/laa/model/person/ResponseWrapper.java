package com.moj.digital.laa.model.person;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseWrapper {
    private final String responseMessage;

    public ResponseWrapper(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
