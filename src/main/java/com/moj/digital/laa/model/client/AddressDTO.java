package com.moj.digital.laa.model.client;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postCode;

}
