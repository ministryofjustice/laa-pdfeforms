package com.moj.digital.laa.controller.client.attendance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
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

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    public void persistClientWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidClientAttendanceDataException("", new Exception())).when(clientAttendanceService).save(any(AttendanceDTO.class));
        AttendanceDTO attendanceDTO = attendanceDTOFromJson();

        mockMvc.perform(post("/client/attendance/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(attendanceDTO)))
                .andExpect(status().isInternalServerError());

        verify(clientAttendanceService, times(1)).save(any(AttendanceDTO.class));
    }

    private AttendanceDTO attendanceDTOFromJson() throws IOException {
        return jsonTestUtil.attendanceDTOFromJson();
    }

    private ClientDTO clientDTOFromJson() throws IOException {
        return jsonTestUtil.clientDTOFromJson();
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        return jsonTestUtil.asJsonString(object);
    }

}