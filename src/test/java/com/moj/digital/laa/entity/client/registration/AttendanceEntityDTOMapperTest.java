package com.moj.digital.laa.entity.client.registration;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.util.JsonTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;

@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class AttendanceEntityDTOMapperTest {
    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void verifyAttendanceDTOtoAttendanceEntityMapping() throws IOException {
        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn("UFN1");

        Attendance attendance = modelMapper.map(attendanceDTO,Attendance.class);
        assertDTOAndEntityAttributes(attendanceDTO, attendance);
    }

    @Test
    public void verifyAttendanceEntityToAttendanceDTOMapping() throws IOException {

        Attendance attendance = jsonTestUtil.attendanceFromJson();
        attendance.setUfn("UFN1");

        AttendanceDTO attendanceDTO = modelMapper.map(attendance,AttendanceDTO.class);
        assertDTOAndEntityAttributes(attendanceDTO, attendance);
    }

    private void assertDTOAndEntityAttributes(AttendanceDTO attendanceDTO, Attendance attendance) {
        assertThat(attendance.getScope()).isEqualTo(attendanceDTO.getScope());
        assertThat(attendance.getAllegations_text()).isEqualTo(attendanceDTO.getAllegations_text());
        assertThat(attendance.getCustody()).isEqualTo(attendanceDTO.getCustody());
        assertThat(attendance.getBail()).isEqualTo(attendanceDTO.getBail());
        assertThat(attendance.getAdvice()).isEqualTo(attendanceDTO.getAdvice());
        assertThat(attendance.getInstructions()).isEqualTo(attendanceDTO.getInstructions());
        assertThat(attendance.getAdvice()).isEqualTo(attendanceDTO.getAdvice());
        assertThat(attendance.getResult()).isEqualTo(attendanceDTO.getResult());
        assertThat(attendance.getActionText()).isEqualTo(attendanceDTO.getActionText());
        assertThat(attendance.getUfn()).isEqualTo(attendanceDTO.getUfn());
        assertThat(attendance.getId()).isEqualTo(attendanceDTO.getId());
    }
}
