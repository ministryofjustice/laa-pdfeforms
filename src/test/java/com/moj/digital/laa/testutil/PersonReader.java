package com.moj.digital.laa.testutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.digital.laa.model.person.PersonDTO;

import java.io.IOException;
import java.io.InputStream;

public class PersonReader {

    public PersonDTO personFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = this.getClass().getResourceAsStream("/integrationtest/person/person.json");
        PersonDTO personDTO = objectMapper.readValue(is,PersonDTO.class);
        return personDTO;
    }

    public static void main(String[] args) throws IOException {
        PersonReader personReader = new PersonReader();
        System.out.println(personReader.personFromJson());
    }
}
