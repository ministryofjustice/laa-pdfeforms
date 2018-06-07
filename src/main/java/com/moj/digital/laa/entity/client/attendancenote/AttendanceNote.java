package com.moj.digital.laa.entity.client.attendancenote;

import com.moj.digital.laa.common.util.StringDateSQLDateConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "attendance_note")
public class AttendanceNote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_attendance_note_generator")
    @SequenceGenerator(name = "client_attendance_note_generator", sequenceName = "attendance_note_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ufn", nullable = false)
    private String ufn;

    @Column(name="attendance_date")
    @Convert(converter = StringDateSQLDateConverter.class)
    private String attendanceDate;

    @Column(name="status")
    private String status;

    @Column(name="free_earner")
    private String freeEarner;

    @Column(name="venue")
    private String venue;

    @Column(name="risk_need_for_check")
    private String riskNeededForCheck;

    @Column(name="conflict_check")
    private String conflictCheck;

    @Column(name="client_attend")
    private String clientAttend;

    @Column(name="client_cps")
    private String clientCps;

    @Column(name="attend_witness")
    private String attendWitness;

    @Column(name="attend_other")
    private String attendOther;

    @Column(name="prep")
    private String prep;

    @Column(name="calls_made")
    private String callsMade;

    @Column(name="travel")
    private String travel;

    @Column(name="advoc")
    private String advoc;

    @Column(name="waiting")
    private String waiting;

    @Column(name="mileage")
    private Double mileage;

    @Column(name="other_disabilities")
    private String otherDisabilities;

    @Column(name="instruction_notes")
    private String instructionNotes;
}
