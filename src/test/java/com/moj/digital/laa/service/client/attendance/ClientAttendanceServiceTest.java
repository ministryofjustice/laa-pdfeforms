package com.moj.digital.laa.service.client.attendance;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.exception.client.attendance.ClientAttendanceNotFoundException;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.repository.client.attendance.ClientAttendanceRepository;
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
public class ClientAttendanceServiceTest {

    private ClientAttendanceService clientAttendanceService;

    @Mock
    private ClientAttendanceRepository clientAttendanceRepository;

    @Before
    public void setup() {
        clientAttendanceService = new ClientAttendanceService(clientAttendanceRepository, new ModelMapper());
    }

    @Test(expected = InvalidClientAttendanceDataException.class)
    public void saveWhenServiceFailureOccursShouldThrowException() {
        when(clientAttendanceRepository.save(any(Attendance.class))).thenThrow(new RuntimeException(""));
        clientAttendanceService.save(new AttendanceDTO());
    }

    @Test
    public void saveWhenThereIsNoInternalErrorClientShouldBeSaved() {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        clientAttendanceService.save(attendanceDTO);
    }

    @Test
    public void findByIdWhenAValidIDIsSentShouldReturnAttendance() {
        Attendance attendance = new Attendance();
        attendance.setUfn("UFN1");

        when(clientAttendanceRepository.findById(12L)).thenReturn(Optional.of(attendance));

        AttendanceDTO attendanceDTO = clientAttendanceService.findById(12L);
        assertThat(attendanceDTO.getUfn()).isEqualTo(attendance.getUfn());
    }

    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnMatchingClients() {
        List<Attendance> listOfClients = new ArrayList<>();

        Attendance attendance1 = new Attendance();
        attendance1.setUfn("UFN1");

        Attendance attendance2 = new Attendance();
        attendance2.setUfn("UFN2");

        listOfClients.add(attendance1);
        listOfClients.add(attendance2);

        when(clientAttendanceRepository.findByUfn("UFN")).thenReturn(listOfClients);
        List<AttendanceDTO> attendanceDTOs = clientAttendanceService.findByUfn("UFN");

        assertThat(listOfClients.size()).isEqualTo(attendanceDTOs.size());
        assertThat(listOfClients.get(0).getUfn()).isEqualTo(attendanceDTOs.get(0).getUfn());
        assertThat(listOfClients.get(1).getUfn()).isEqualTo(attendanceDTOs.get(1).getUfn());
    }

    @Test(expected = ClientAttendanceNotFoundException.class)
    public void findByIdWhenAnInValidUFNIsSentShouldThrowAnException() {
        when(clientAttendanceRepository.findById(12L)).thenReturn(Optional.empty());
        clientAttendanceService.findById(12L);
    }

    @Test(expected = InvalidClientAttendanceDataException.class)
    public void updateWhenServiceFailureOccursShouldThrowException() {
        when(clientAttendanceRepository.findById(12L)).thenReturn(Optional.of(new Attendance()));
        when(clientAttendanceRepository.save(any(Attendance.class))).thenThrow(new RuntimeException(""));

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(12L);
        attendanceDTO.setUfn("UFN1");

        clientAttendanceService.update(attendanceDTO);

        verify(clientAttendanceRepository).save(Mockito.any(Attendance.class));
    }

    @Test
    public void updateWhenThereIsNoInternalErrorAttendanceShouldBeSaved() {
        when(clientAttendanceRepository.findById(12L)).thenReturn(Optional.of(new Attendance()));

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(12L);
        attendanceDTO.setUfn("UFN1");

        clientAttendanceService.update(attendanceDTO);

        verify(clientAttendanceRepository).save(Mockito.any(Attendance.class));
    }

}