package com.moj.digital.laa.model.client.attendancenote;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class AttendanceNoteDTO {

    private Long id;

    @NotEmpty
    private String ufn;

    private LocalDate attendanceDate;
    private String status;
    private String freeEarner;
    private String venue;
    private String riskNeededForCheck;
    private String conflictCheck;
    private String clientAttend;
    private String clientCps;
    private String attendWitness;
    private String attendOther;
    private String prep;
    private String callsMade;
    private String travel;
    private String advoc;
    private String waiting;
    private Double mileage;
    private String otherDisabilities;
    private String instructionNotes;
}
