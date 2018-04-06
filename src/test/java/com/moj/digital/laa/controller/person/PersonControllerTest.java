package com.moj.digital.laa.controller.person;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.exception.person.InvalidPersonDataException;
import com.moj.digital.laa.exception.person.PersonNotFoundException;
import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.service.person.PersonService;
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
import java.io.InputStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import({FieldsErrorExtractor.class})
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    public void persistPersonWhenServiceFailureOccursShouldReturnInternalServerErrorStatusCode() throws Exception {
        doThrow(new InvalidPersonDataException("", new Exception())).when(personService).save(any(PersonDTO.class));
        PersonDTO personDTO = personFromJson();

        mockMvc.perform(post("/person/persist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isInternalServerError());

        verify(personService, times(1)).save(any(PersonDTO.class));
    }

    @Test
    public void persistPersonWhenInputsAreValidShouldSavePerson() throws Exception {
        doNothing().when(personService).save(any(PersonDTO.class));
        PersonDTO personDTO = personFromJson();

        mockMvc.perform(post("/person/persist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isCreated());

        verify(personService, times(1)).save(any(PersonDTO.class));
    }

    @Test
    public void persistPersonWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {
        PersonDTO personDTO = personFromJson();
        personDTO.setTitle(null);

        mockMvc.perform(post("/person/persist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isBadRequest());

        verify(personService, never()).save(any(PersonDTO.class));
    }

    @Test
    public void findPersonByUfnWhenValidUFNPassedShouldReturnCorrespondingPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setUfn("UFN1");
        personDTO.setForeName("David");

        given(personService.findByUfn("UFN1")).willReturn(personDTO);

        mockMvc.perform(get("/person/UFN1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ufn").value("UFN1"))
                .andExpect(jsonPath("foreName").value("David"));

        verify(personService, times(1)).findByUfn("UFN1");

    }

    @Test
    public void findPersonByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() throws Exception {
        given(personService.findByUfn("UFN1")).willThrow(new PersonNotFoundException(""));

        mockMvc.perform(get("/person/{ufn}", "UFN1"))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).findByUfn("UFN1");
    }



    @Test
    public void updatePersonWhenInputsAreValidShouldSavePerson() throws Exception {
        doNothing().when(personService).update(any(PersonDTO.class));

        PersonDTO personDTO = personFromJson();
        personDTO.setUfn("UFN1");

        mockMvc.perform(put("/person/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isCreated());

        verify(personService, times(1)).update(any(PersonDTO.class));

    }

    @Test
    public void updatePersonWhenNotAbleToLocatePersonShouldReturnNotFoundStatusCode() throws Exception {
        doThrow(new PersonNotFoundException("")).when(personService).update(any(PersonDTO.class));

        PersonDTO personDTO = personFromJson();
        personDTO.setUfn("UFN1");

        mockMvc.perform(put("/person/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).update(any(PersonDTO.class));

    }

    @Test
    public void updatePersonWhenInputsAreInValidShouldReturnBadRequestStatusCode() throws Exception {

        PersonDTO personDTO = personFromJson();
        personDTO.setTitle(null);

        mockMvc.perform(put("/person/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(status().isBadRequest());

        verify(personService, never()).update(any(PersonDTO.class));
    }

    private PersonDTO personFromJson() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/integrationtest/person/person.json");
        PersonDTO personDTO = objectMapper.readValue(is, PersonDTO.class);
        return personDTO;
    }

    private String asJsonString(final PersonDTO personDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDTO);
    }
}