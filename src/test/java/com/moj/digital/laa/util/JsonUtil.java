package com.moj.digital.laa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.digital.laa.common.config.MapperConfiguration;
import com.moj.digital.laa.entity.person.Person;
import com.moj.digital.laa.model.person.PersonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;

@TestComponent
@Import(MapperConfiguration.class)
public class JsonUtil {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    //TODO remove the hard coding of the person.json file and move it to the profile/properties
    public PersonDTO personDTOFromJson() throws IOException {
        InputStream is = JsonUtil.class.getResourceAsStream("/integrationtest/person/person.json");
        PersonDTO personDTO = objectMapper.readValue(is, PersonDTO.class);
        return personDTO;
    }

    public Person personFromJson() throws IOException {
        return modelMapper.map(personDTOFromJson(),Person.class);
    }

    public String asJsonString(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
