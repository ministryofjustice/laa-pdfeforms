package com.moj.digital.laa.controller.client.attendancenote;

import com.moj.digital.laa.exception.client.attendance.ClientAttendanceNotFoundException;
import com.moj.digital.laa.exception.client.attendancenote.InvalidClientAttendanceNoteDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.service.client.attendancenote.ClientAttendanceNoteService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientAttendanceNoteController.class)
@Import({FieldsErrorExtractor.class, JsonTestUtil.class})
public class ClientAttendanceNoteControllerTest {
    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientAttendanceNoteService clientAttendanceNoteService;

    @Test
    public void persistAttendanceWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidClientAttendanceNoteDataException("", new Exception())).when(clientAttendanceNoteService).save(any(AttendanceNoteDTO.class));
        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();

        mockMvc.perform(post("/client/attendanceNote/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isInternalServerError());

        verify(clientAttendanceNoteService, times(1)).save(any(AttendanceNoteDTO.class));
    }

    @Test
    public void persistAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        doReturn(-1L).when(clientAttendanceNoteService).save(any(AttendanceNoteDTO.class));
        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();

        mockMvc.perform(post("/client/attendanceNote/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isCreated());

        verify(clientAttendanceNoteService, times(1)).save(any(AttendanceNoteDTO.class));
    }

    @Test
    public void persistAttendanceWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {
        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceDTO.setUfn(null);

        mockMvc.perform(post("/client/attendanceNote/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isBadRequest());

        verify(clientAttendanceNoteService, never()).save(any(AttendanceNoteDTO.class));
    }

    @Test
    public void findAttendanceByIDWhenValidUFNPassedShouldReturnCorrespondingAttendance() throws Exception {
        AttendanceNoteDTO attendanceDTO = new AttendanceNoteDTO();
        attendanceDTO.setUfn("UFN1");
        attendanceDTO.setId(12L);

        given(clientAttendanceNoteService.findById(12L)).willReturn(attendanceDTO);

        mockMvc.perform(get("/client/attendanceNote/forID/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(12L))
                .andExpect(jsonPath("ufn").value("UFN1"));

        verify(clientAttendanceNoteService, times(1)).findById(12L);

    }

    @Test
    public void findAttendanceByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() throws Exception {
        given(clientAttendanceNoteService.findById(12L)).willThrow(new ClientAttendanceNotFoundException(""));

        mockMvc.perform(get("/client/attendanceNote/forID/12"))
                .andExpect(status().isNotFound());

        verify(clientAttendanceNoteService, times(1)).findById(12L);
    }


    @Test
    public void findAttendancesByUfnWhenValidUFNPassedShouldReturnListOfMatchingRecords() throws Exception {
        List<AttendanceNoteDTO> attendanceDTOList = new ArrayList<>();

        AttendanceNoteDTO attendanceDTO1 = new AttendanceNoteDTO();
        attendanceDTO1.setUfn("UFN1");
        attendanceDTO1.setId(12L);


        AttendanceNoteDTO attendanceDTO2 = new AttendanceNoteDTO();
        attendanceDTO2.setUfn("UFN1");
        attendanceDTO2.setId(13L);


        attendanceDTOList.add(attendanceDTO1);
        attendanceDTOList.add(attendanceDTO2);

        given(clientAttendanceNoteService.findByUfn("UFN1")).willReturn(attendanceDTOList);

        mockMvc.perform(get("/client/attendanceNote/allForUFN/UFN1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", equalTo(12)))
                .andExpect(jsonPath("$.[1].id", equalTo(13)))
                .andExpect(jsonPath("$.[0].ufn", equalTo("UFN1")))
                .andExpect(jsonPath("$.[1].ufn", equalTo("UFN1")));

        verify(clientAttendanceNoteService, times(1)).findByUfn("UFN1");

    }

    @Test
    public void updateAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        doReturn(-1L).when(clientAttendanceNoteService).update(any(AttendanceNoteDTO.class));

        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/attendanceNote/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isAccepted());

        verify(clientAttendanceNoteService, times(1)).update(any(AttendanceNoteDTO.class));

    }


    @Test
    public void updateAttendanceWhenNotAbleToLocateAttendanceShouldReturnNotFoundStatusCode() throws Exception {
        doThrow(new ClientAttendanceNotFoundException("")).when(clientAttendanceNoteService).update(any(AttendanceNoteDTO.class));

        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/attendanceNote/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isNotFound());

        verify(clientAttendanceNoteService, times(1)).update(any(AttendanceNoteDTO.class));

    }


    @Test
    public void updateAttendanceWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {

        AttendanceNoteDTO attendanceDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceDTO.setUfn(null);

        mockMvc.perform(put("/client/attendanceNote/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUtil.asJsonString(attendanceDTO)))
                .andExpect(status().isBadRequest());

        verify(clientAttendanceNoteService, never()).update(any(AttendanceNoteDTO.class));
    }
    

}