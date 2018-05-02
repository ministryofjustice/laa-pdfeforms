package com.moj.digital.laa.model.client.registration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisabilityDTO {
    private Long id;
    private Long clientID;
    private String disabilityOption;
}
