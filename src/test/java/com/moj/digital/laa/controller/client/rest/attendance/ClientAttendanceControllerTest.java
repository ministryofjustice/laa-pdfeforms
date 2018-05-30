package com.moj.digital.laa.controller.client.rest.attendance;

import com.moj.digital.laa.exception.client.attendance.ClientAttendanceNotFoundException;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.service.client.attendance.ClientAttendanceService;
import com.moj.digital.laa.util.JsonTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(ClientAttendanceController.class)
@Import({FieldsErrorExtractor.class, JsonTestUtil.class})

public class ClientAttendanceControllerTest {

    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientAttendanceService clientAttendanceService;

    @Test
    public void persistAttendanceWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidClientAttendanceDataException("", new Exception())).when(clientAttendanceService).save(any(AttendanceDTO.class));
        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();

        mockMvc.perform(post("/client/attendance/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isInternalServerError());

        verify(clientAttendanceService, times(1)).save(any(AttendanceDTO.class));
    }

    @Test
    public void persistAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        doReturn(-1L).when(clientAttendanceService).save(any(AttendanceDTO.class));
        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();

        mockMvc.perform(post("/client/attendance/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isCreated());

        verify(clientAttendanceService, times(1)).save(any(AttendanceDTO.class));
    }

    @Test
    public void persistAttendanceWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {
        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn(null);

        mockMvc.perform(post("/client/attendance/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isBadRequest());

        verify(clientAttendanceService, never()).save(any(AttendanceDTO.class));
    }

    @Test
    public void findAttendanceByIDWhenValidUFNPassedShouldReturnCorrespondingAttendance() throws Exception {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setUfn("UFN1");
        attendanceDTO.setId(12L);

        given(clientAttendanceService.findById(12L)).willReturn(attendanceDTO);

        mockMvc.perform(get("/client/attendance/forID/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(12L))
                .andExpect(jsonPath("ufn").value("UFN1"));

        verify(clientAttendanceService, times(1)).findById(12L);

    }

    @Test
    public void findAttendanceByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() throws Exception {
        given(clientAttendanceService.findById(12L)).willThrow(new ClientAttendanceNotFoundException(""));

        mockMvc.perform(get("/client/attendance/forID/12"))
                .andExpect(status().isNotFound());

        verify(clientAttendanceService, times(1)).findById(12L);
    }


    @Test
    public void findAttendancesByUfnWhenValidUFNPassedShouldReturnListOfMatchingRecords() throws Exception {
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();

        AttendanceDTO attendanceDTO1 = new AttendanceDTO();
        attendanceDTO1.setUfn("UFN1");
        attendanceDTO1.setId(12L);


        AttendanceDTO attendanceDTO2 = new AttendanceDTO();
        attendanceDTO2.setUfn("UFN1");
        attendanceDTO2.setId(13L);


        attendanceDTOList.add(attendanceDTO1);
        attendanceDTOList.add(attendanceDTO2);

        given(clientAttendanceService.findByUfn("UFN1")).willReturn(attendanceDTOList);

        mockMvc.perform(get("/client/attendance/allForUFN/UFN1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", equalTo(12)))
                .andExpect(jsonPath("$.[1].id", equalTo(13)))
                .andExpect(jsonPath("$.[0].ufn", equalTo("UFN1")))
                .andExpect(jsonPath("$.[1].ufn", equalTo("UFN1")));

        verify(clientAttendanceService, times(1)).findByUfn("UFN1");

    }

    @Test
    public void updateAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        doReturn(-1L).when(clientAttendanceService).update(any(AttendanceDTO.class));

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/attendance/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isAccepted());

        verify(clientAttendanceService, times(1)).update(any(AttendanceDTO.class));

    }


    @Test
    public void updateAttendanceWhenNotAbleToLocateAttendanceShouldReturnNotFoundStatusCode() throws Exception {
        doThrow(new ClientAttendanceNotFoundException("")).when(clientAttendanceService).update(any(AttendanceDTO.class));

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/attendance/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isNotFound());

        verify(clientAttendanceService, times(1)).update(any(AttendanceDTO.class));

    }


    @Test
    public void updateAttendanceWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn(null);

        mockMvc.perform(put("/client/attendance/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isBadRequest());

        verify(clientAttendanceService, never()).update(any(AttendanceDTO.class));
    }


}