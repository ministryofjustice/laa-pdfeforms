package com.moj.digital.laa.exception;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ErrorDetails implements Serializable {
    private LocalDateTime date;
    private String message;
    private String[] details;

    public ErrorDetails(LocalDateTime date, String message, String... details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }
}