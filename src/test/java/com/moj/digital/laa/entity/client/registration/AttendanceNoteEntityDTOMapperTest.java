package com.moj.digital.laa.entity.client.registration;

import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
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

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class AttendanceNoteEntityDTOMapperTest {
    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void verifyAttendanceNoteDTOtoAttendanceNoteEntityMapping() throws IOException {
        AttendanceNoteDTO attendanceNoteDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceNoteDTO.setUfn("UFN1");

        AttendanceNote attendanceNote = modelMapper.map(attendanceNoteDTO,AttendanceNote.class);
        assertDTOAndEntityAttributes(attendanceNoteDTO, attendanceNote);
    }

    @Test
    public void verifyAttendanceNoteEntityToAttendanceNoteDTOMapping() throws IOException {

        AttendanceNote attendanceNote = jsonTestUtil.attendanceNoteFromJson();
        attendanceNote.setUfn("UFN1");

        AttendanceNoteDTO attendanceNoteDTO = modelMapper.map(attendanceNote,AttendanceNoteDTO.class);
        assertDTOAndEntityAttributes(attendanceNoteDTO, attendanceNote);
    }

    private void assertDTOAndEntityAttributes(AttendanceNoteDTO attendanceNoteDTO, AttendanceNote attendanceNote) {
        assertThat(attendanceNote.getAttendanceDate()).isEqualTo(attendanceNoteDTO.getAttendanceDate());
        assertThat(attendanceNote.getStatus()).isEqualTo(attendanceNoteDTO.getStatus());
        assertThat(attendanceNote.getFreeEarner()).isEqualTo(attendanceNoteDTO.getFreeEarner());
        assertThat(attendanceNote.getVenue()).isEqualTo(attendanceNoteDTO.getVenue());
        assertThat(attendanceNote.getRiskNeededForCheck()).isEqualTo(attendanceNoteDTO.getRiskNeededForCheck());
        assertThat(attendanceNote.getConflictCheck()).isEqualTo(attendanceNoteDTO.getConflictCheck());
        assertThat(attendanceNote.getClientAttend()).isEqualTo(attendanceNoteDTO.getClientAttend());
        assertThat(attendanceNote.getClientCps()).isEqualTo(attendanceNoteDTO.getClientCps());
        assertThat(attendanceNote.getAttendWitness()).isEqualTo(attendanceNoteDTO.getAttendWitness());

        assertThat(attendanceNote.getAttendOther()).isEqualTo(attendanceNoteDTO.getAttendOther());
        assertThat(attendanceNote.getPrep()).isEqualTo(attendanceNoteDTO.getPrep());
        assertThat(attendanceNote.getCallsMade()).isEqualTo(attendanceNoteDTO.getCallsMade());
        assertThat(attendanceNote.getTravel()).isEqualTo(attendanceNoteDTO.getTravel());
        assertThat(attendanceNote.getAdvoc()).isEqualTo(attendanceNoteDTO.getAdvoc());
        assertThat(attendanceNote.getWaiting()).isEqualTo(attendanceNoteDTO.getWaiting());
        assertThat(attendanceNote.getMileage()).isEqualTo(attendanceNoteDTO.getMileage());
        assertThat(attendanceNote.getOtherDisabilities()).isEqualTo(attendanceNoteDTO.getOtherDisabilities());
        assertThat(attendanceNote.getInstructionNotes()).isEqualTo(attendanceNoteDTO.getInstructionNotes());


        assertThat(attendanceNote.getUfn()).isEqualTo(attendanceNoteDTO.getUfn());
        assertThat(attendanceNote.getId()).isEqualTo(attendanceNoteDTO.getId());
    }
}
