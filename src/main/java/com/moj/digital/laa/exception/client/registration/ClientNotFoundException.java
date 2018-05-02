package com.moj.digital.laa.exception.client.registration;

import com.moj.digital.laa.exception.client.common.EntityNotFoundException;

public class ClientNotFoundException extends EntityNotFoundException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
