package com.moj.digital.laa.service.client.attendancenote;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.exception.client.attendancenote.ClientAttendanceNoteNotFoundException;
import com.moj.digital.laa.exception.client.attendancenote.InvalidClientAttendanceNoteDataException;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.repository.client.attendancenote.ClientAttendanceNoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Import({ModelMapper.class})
public class ClientAttendanceNoteServiceTest {
    private ClientAttendanceNoteService clientAttendanceNoteService;

    @Mock
    private ClientAttendanceNoteRepository clientAttendanceNoteRepository;

    @Before
    public void setup() {
        clientAttendanceNoteService = new ClientAttendanceNoteService(clientAttendanceNoteRepository, new ModelMapper());
    }

    @Test(expected = InvalidClientAttendanceNoteDataException.class)
    public void saveWhenServiceFailureOccursShouldThrowException() {
        when(clientAttendanceNoteRepository.save(any(AttendanceNote.class))).thenThrow(new RuntimeException(""));
        clientAttendanceNoteService.save(new AttendanceNoteDTO());
    }

    @Test
    public void saveWhenThereIsNoInternalErrorClientShouldBeSaved() {
        AttendanceNote attendanceNote = new AttendanceNote();
        attendanceNote.setId(12L);

        when(clientAttendanceNoteRepository.save(any(AttendanceNote.class))).thenReturn(attendanceNote);

        AttendanceNoteDTO attendanceNoteDTO = new AttendanceNoteDTO();
        Long savedId = clientAttendanceNoteService.save(attendanceNoteDTO);

        assertThat(savedId).isEqualTo(attendanceNote.getId());
    }

    @Test
    public void findByIdWhenAValidIDIsSentShouldReturnAttendance() {
        AttendanceNote attendance = new AttendanceNote();
        attendance.setUfn("UFN1");

        when(clientAttendanceNoteRepository.findById(12L)).thenReturn(Optional.of(attendance));

        AttendanceNoteDTO attendanceDTO = clientAttendanceNoteService.findById(12L);
        assertThat(attendanceDTO.getUfn()).isEqualTo(attendance.getUfn());
    }

    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnMatchingClients() {
        List<AttendanceNote> listOfClients = new ArrayList<>();

        AttendanceNote attendance1 = new AttendanceNote();
        attendance1.setUfn("UFN1");

        AttendanceNote attendance2 = new AttendanceNote();
        attendance2.setUfn("UFN2");

        listOfClients.add(attendance1);
        listOfClients.add(attendance2);

        when(clientAttendanceNoteRepository.findByUfn("UFN")).thenReturn(listOfClients);
        List<AttendanceNoteDTO> clientDTOS = clientAttendanceNoteService.findByUfn("UFN");

        assertThat(listOfClients.size()).isEqualTo(clientDTOS.size());
        assertThat(listOfClients.get(0).getUfn()).isEqualTo(clientDTOS.get(0).getUfn());
        assertThat(listOfClients.get(1).getUfn()).isEqualTo(clientDTOS.get(1).getUfn());
    }

    @Test(expected = ClientAttendanceNoteNotFoundException.class)
    public void findByIdWhenAnInValidUFNIsSentShouldThrowAnException() {
        when(clientAttendanceNoteRepository.findById(12L)).thenReturn(Optional.empty());
        clientAttendanceNoteService.findById(12L);
    }

    @Test(expected = InvalidClientAttendanceNoteDataException.class)
    public void updateWhenServiceFailureOccursShouldThrowException() {
        when(clientAttendanceNoteRepository.findById(12L)).thenReturn(Optional.of(new AttendanceNote()));
        when(clientAttendanceNoteRepository.save(any(AttendanceNote.class))).thenThrow(new RuntimeException(""));

        AttendanceNoteDTO attendanceDTO = new AttendanceNoteDTO();
        attendanceDTO.setId(12L);
        attendanceDTO.setUfn("UFN1");

        clientAttendanceNoteService.update(attendanceDTO);

        verify(clientAttendanceNoteRepository).save(Mockito.any(AttendanceNote.class));
    }

    @Test
    public void updateWhenThereIsNoInternalErrorAttendanceShouldBeSaved() {

        AttendanceNote attendanceNote = new AttendanceNote();
        attendanceNote.setId(12L);
        when(clientAttendanceNoteRepository.findById(12L)).thenReturn(Optional.of(attendanceNote));

        when(clientAttendanceNoteRepository.save(any(AttendanceNote.class))).thenReturn(attendanceNote);



        AttendanceNoteDTO attendanceDTO = new AttendanceNoteDTO();
        attendanceDTO.setId(12L);
        attendanceDTO.setUfn("UFN1");

        clientAttendanceNoteService.update(attendanceDTO);

        verify(clientAttendanceNoteRepository).save(Mockito.any(AttendanceNote.class));
    }

}