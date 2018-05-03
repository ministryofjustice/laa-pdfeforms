package com.moj.digital.laa.controller.client.registration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.moj.digital.laa.exception.client.registration.ClientNotFoundException;
import com.moj.digital.laa.exception.client.registration.InvalidClientRegistrationDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.service.client.registration.ClientRegistrationService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientRegistrationController.class)
@Import({FieldsErrorExtractor.class,JsonTestUtil.class})
public class ClientRegistrationControllerTest {

    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRegistrationService clientRegistrationService;

    @Test
    public void persistClientWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidClientRegistrationDataException("", new Exception())).when(clientRegistrationService).save(any(ClientDTO.class));
        ClientDTO clientDTO = clientDTOFromJson();

        mockMvc.perform(post("/client/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isInternalServerError());

        verify(clientRegistrationService, times(1)).save(any(ClientDTO.class));
    }

    @Test
    public void persistClientWhenInputsAreValidShouldSaveClient() throws Exception {
        doNothing().when(clientRegistrationService).save(any(ClientDTO.class));
        ClientDTO clientDTO = clientDTOFromJson();

        mockMvc.perform(post("/client/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isCreated());

        verify(clientRegistrationService, times(1)).save(any(ClientDTO.class));
    }

    @Test
    public void persistClientWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {
        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setTitle(null);

        mockMvc.perform(post("/client/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isBadRequest());

        verify(clientRegistrationService, never()).save(any(ClientDTO.class));
    }

    @Test
    public void findClientByUfnWhenValidUFNPassedShouldReturnCorrespondingClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setUfn("UFN1");
        clientDTO.setForename("David");

        given(clientRegistrationService.findByUfn("UFN1")).willReturn(clientDTO);

        mockMvc.perform(get("/client/UFN1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ufn").value("UFN1"))
                .andExpect(jsonPath("forename").value("David"));

        verify(clientRegistrationService, times(1)).findByUfn("UFN1");

    }

    @Test
    public void findClientByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() throws Exception {
        given(clientRegistrationService.findByUfn("UFN1")).willThrow(new ClientNotFoundException(""));

        mockMvc.perform(get("/client/{ufn}", "UFN1"))
                .andExpect(status().isNotFound());

        verify(clientRegistrationService, times(1)).findByUfn("UFN1");
    }

    @Test
    public void findClientByUfnContainingWhenValidUFNPassedShouldReturnListOfMatchingRecords() throws Exception {
        List<ClientDTO> clientDTOList = new ArrayList<>();

        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setUfn("UFN12");
        clientDTO1.setId(12L);


        ClientDTO clientDTO2 = new ClientDTO();
        clientDTO2.setUfn("UFN13");
        clientDTO2.setId(13L);


        clientDTOList.add(clientDTO1);
        clientDTOList.add(clientDTO2);

        given(clientRegistrationService.findClientByUfnContaining("UFN1")).willReturn(clientDTOList);

        mockMvc.perform(get("/client/containingUfn/UFN1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", equalTo(12)))
                .andExpect(jsonPath("$.[1].id", equalTo(13)))
                .andExpect(jsonPath("$.[0].ufn", equalTo("UFN12")))
                .andExpect(jsonPath("$.[1].ufn", equalTo("UFN13")));

        verify(clientRegistrationService, times(1)).findClientByUfnContaining("UFN1");

    }

    @Test
    public void updateClientWhenInputsAreValidShouldSaveClient() throws Exception {
        doNothing().when(clientRegistrationService).update(any(ClientDTO.class));

        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isAccepted());

        verify(clientRegistrationService, times(1)).update(any(ClientDTO.class));

    }

    @Test
    public void updateClientWhenNotAbleToLocateClientShouldReturnNotFoundStatusCode() throws Exception {
        doThrow(new ClientNotFoundException("")).when(clientRegistrationService).update(any(ClientDTO.class));

        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setUfn("UFN1");

        mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isNotFound());

        verify(clientRegistrationService, times(1)).update(any(ClientDTO.class));

    }

    @Test
    public void updateClientWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {

        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setTitle(null);

        mockMvc.perform(put("/client/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isBadRequest());

        verify(clientRegistrationService, never()).update(any(ClientDTO.class));
    }

    private ClientDTO clientDTOFromJson() throws IOException {
        return jsonTestUtil.clientDTOFromJson();
    }

    private String asJsonString(ClientDTO clientDTO) throws JsonProcessingException {
        return jsonTestUtil.asJsonString(clientDTO);
    }
}