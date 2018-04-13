package com.moj.digital.laa.entity.person;

import com.moj.digital.laa.common.util.LocalDateSQLDateConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name = "person_generator", sequenceName = "person_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ufn", nullable = false)
    private String ufn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "forename", nullable = false)
    private String forename;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "residence_address_id")
    private Address residenceAddress;

    @Column(name = "date_of_birth", nullable = false)
    @Convert(converter = LocalDateSQLDateConverter.class)
    private LocalDate dateOfBirth;

    @Column(name = "ni_number", nullable = false)
    private String niNumber;

    @Column(name = "employment_status", nullable = false)
    private String employmentStatus;

    @Column(name = "relationship_status", nullable = false)
    private String relationshipStatus;

    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correspondence_address_id")
    private Address correspondenceAddress;

    @Column(name = "ethnicity", nullable = false)
    private String ethnicity;

    @Column(name = "source_of_business", nullable = false)
    private String sourceOfBusiness;

    @Column(name = "existing_client", nullable = false)
    private String existingClient;

    @Column(name = "request_specific_solicitor", nullable = false)
    private String requestSpecificSolicitor;

    @Column(name = "request_specific_solicitor_text", nullable = false)
    private String requestSpecificSolicitorText;

    @Column(name = "police_station_1", nullable = false)
    private String policeStation1;

    @Column(name = "magistrate_court", nullable = false)
    private String magistrateCourt;

    @Column(name = "crown_court", nullable = false)
    private String crownCourt;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "allegation", nullable = false)
    private String allegation;

    @Column(name = "previous_conviction", nullable = false)
    private String previousConviction;

    @Column(name = "funding_date", nullable = false)
    @Convert(converter = LocalDateSQLDateConverter.class)
    private LocalDate fundingDate;

    @Column(name = "advice_and_assistance", nullable = false)
    private String adviceAndAssistance;

    @Column(name = "proof_of_benefits_requested", nullable = false)
    private String proofOfBenefitsRequested;

    @Column(name = "police_station_2", nullable = false)
    private String policeStation2;

    @Column(name = "pre_order_work", nullable = false)
    private String preOrderWork;

    @Column(name = "rep_order_applied_for", nullable = false)
    private String repOrderAppliedFor;

    @Column(name = "rep_order_granted", nullable = false)
    private String repOrderGranted;

    @Column(name = "crm3_advocate", nullable = false)
    private String crm3Advocate;

    @Column(name = "conflict_check", nullable = false)
    private String conflictCheck;

    @Column(name = "conflict_check_name", nullable = false)
    private String conflictCheckName;

    @Column(name = "conflict_check_date", nullable = false)
    @Convert(converter = LocalDateSQLDateConverter.class)
    private LocalDate conflictCheckDate;

    @Column(name = "risk_assessment", nullable = false)
    private String riskAssessment;

    @Column(name = "co_accused", nullable = false)
    private String coAccused;

}

