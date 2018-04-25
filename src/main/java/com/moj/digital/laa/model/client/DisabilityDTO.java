package com.moj.digital.laa.model.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisabilityDTO {
    private Long id;
    private Long clientID;
    private String disabilityOption;
}
