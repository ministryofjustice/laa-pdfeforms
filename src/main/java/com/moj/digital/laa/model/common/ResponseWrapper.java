package com.moj.digital.laa.model.common;

import lombok.Data;

@Data
public class ResponseWrapper {
    private Long id;
    private String responseMessage;

    public ResponseWrapper(){
        super();
    }

    public ResponseWrapper(Long id, String responseMessage) {
        this.id = id;
        this.responseMessage = responseMessage;
    }
}
