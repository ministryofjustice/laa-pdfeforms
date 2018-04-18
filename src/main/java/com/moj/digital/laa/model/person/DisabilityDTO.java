package com.moj.digital.laa.model.person;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisabilityDTO {
    private Long id;
    private String ufn;
    private String disabilityOption;
}
