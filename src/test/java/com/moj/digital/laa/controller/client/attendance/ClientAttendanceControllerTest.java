package com.moj.digital.laa.controller.client.attendance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.service.client.attendance.ClientAttendanceService;
import com.moj.digital.laa.util.JsonUtil;
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
@Import({FieldsErrorExtractor.class, JsonUtil.class})

public class ClientAttendanceControllerTest {

    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientAttendanceService clientAttendanceService;

    @Test
    public void persistClientWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidClientAttendanceDataException("", new Exception())).when(clientAttendanceService).save(any(AttendanceDTO.class));
        ClientDTO clientDTO = clientDTOFromJson();

        mockMvc.perform(post("/client/attendance/make")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isInternalServerError());

        verify(clientAttendanceService, times(1)).save(any(AttendanceDTO.class));
    }

    private ClientDTO clientDTOFromJson() throws IOException {
        return jsonUtil.clientDTOFromJson();
    }

    private String asJsonString(ClientDTO clientDTO) throws JsonProcessingException {
        return jsonUtil.asJsonString(clientDTO);
    }

}