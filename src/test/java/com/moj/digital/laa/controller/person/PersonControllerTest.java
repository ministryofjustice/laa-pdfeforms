package com.moj.digital.laa.controller.person;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.service.person.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    public void  test () throws IOException {
        PersonDTO personDTO = personFromJson();
    }

    private PersonDTO personFromJson() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/integrationtest/person/person.json");
        PersonDTO personDTO = objectMapper.readValue(is,PersonDTO.class);
        return personDTO;
    }
}