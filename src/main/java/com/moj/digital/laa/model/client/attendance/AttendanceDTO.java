package com.moj.digital.laa.model.client.attendance;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AttendanceDTO {

    private Long id;

    @NotEmpty
    private String ufn;

    private String scope;

    private String allegations;

    private String custody;

    private String bail;

    private String allegations_text;

    private String instructions;

    private String advice;

    private String result;

    private String action;
}
