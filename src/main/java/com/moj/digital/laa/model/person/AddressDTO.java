package com.moj.digital.laa.model.person;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postCode;

    private String type;
}
