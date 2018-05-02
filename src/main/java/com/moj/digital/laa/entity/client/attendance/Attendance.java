package com.moj.digital.laa.entity.client.attendance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_attendance_generator")
    @SequenceGenerator(name = "client_attendance_generator", sequenceName = "attendance_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ufn", nullable = false)
    private String ufn;

    @Column(name="scope")
    private String scope;

    @Column(name="allegations")
    private String allegations;

    @Column(name="custody")
    private String custody;

    @Column(name="bail")
    private String bail;

    @Column(name="allegations_text")
    private String allegations_text;

    @Column(name="instructions")
    private String instructions;

    @Column(name="advice")
    private String advice;

    @Column(name="result")
    private String result;

    @Column(name="action_text")
    private String actionText;
}
