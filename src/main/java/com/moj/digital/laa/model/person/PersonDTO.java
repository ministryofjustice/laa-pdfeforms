package com.moj.digital.laa.model.person;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PersonDTO {

    private Long id;

    @NotNull
    private String ufn;

    @NotNull
    private String title;

    private String surname;

    private String forename;

    private AddressDTO residenceAddress;

    private LocalDate dateOfBirth;

    private String niNumber;

    private String employmentStatus;

    private String relationshipStatus;

    private String telephoneNumber;

    private AddressDTO correspondenceAddress;

    private String ethnicity;

    private String sourceOfBusiness;

    private String existingClient;

    private String requestSpecificSolicitor;

    private String requestSpecificSolicitorText;

    private String policeStation1;

    private String magistrateCourt;

    private String crownCourt;

    private String otherCourt;

    private String nationality;

    private String venue;

    private String allegation;

    private String previousConviction;

    private LocalDate fundingDate;

    private String adviceAndAssistance;

    private String proofOfBenefitsRequested;

    private String policeStation2;

    private String preOrderWork;

    private String repOrderAppliedFor;

    private String repOrderGranted;

    private String crm3Advocate;

    private String conflictCheck;

    private String conflictCheckName;

    private LocalDate conflictCheckDate;

    private String riskAssessmentDone;

    private String coAccused;

}
