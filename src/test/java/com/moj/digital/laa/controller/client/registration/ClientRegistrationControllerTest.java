package com.moj.digital.laa.controller.client.registration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.moj.digital.laa.exception.client.registration.ClientNotFoundException;
import com.moj.digital.laa.exception.client.registration.InvalidClientRegistrationDataException;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.service.client.registration.ClientRegistrationService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({ClientRegistrationController.class})
@Import({FieldsErrorExtractor.class,JsonUtil.class})
public class ClientRegistrationControllerTest {

    @Autowired
    private JsonUtil jsonUtil;

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
        return jsonUtil.clientDTOFromJson();
    }

    private String asJsonString(ClientDTO clientDTO) throws JsonProcessingException {
        return jsonUtil.asJsonString(clientDTO);
    }
}