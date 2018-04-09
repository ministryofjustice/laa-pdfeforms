package com.moj.digital.laa;

import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JsonUtil.class)
@AutoConfigureTestDatabase
public class PDFeFormsApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JsonUtil jsonUtil;

    @Test
    public void persistPersonWhenInputsAreValidShouldSavePerson() throws Exception {
        PersonDTO personDTO = personDTOFromJson();
        personDTO.setUfn("UFN1");
        ResponseEntity<String> result = testRestTemplate.postForEntity
                ("/person/persist", httpEntity(personDTO), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistPersonShouldNotAllowPersonsWithSameUFNTobeSaved() throws Exception {
        PersonDTO personDTO = personDTOFromJson();
        personDTO.setUfn("UFN2");
        testRestTemplate.postForEntity
                ("/person/persist", httpEntity(personDTO), String.class);

        personDTO.setUfn("UFN2");
        ResponseEntity<String> result = testRestTemplate.postForEntity
                ("/person/persist", httpEntity(personDTO), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updatePersonWhenTryingToUpdateAnExistingPersonShouldReturnCreatedStatusCode() throws Exception {
        PersonDTO personDTO = personDTOFromJson();
        personDTO.setUfn("UFN3");

        ResponseEntity<String> createResult = testRestTemplate.postForEntity
                ("/person/persist", httpEntity(personDTO), String.class);

        personDTO.setUfn("UFN3");
        ResponseEntity<String> updateResult = testRestTemplate.exchange("/person/update", HttpMethod.PUT, httpEntity(personDTO), String.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updatePersonWhenTryingToUpdateANonExistingPersonShouldReturnNotFoundHttpStatusCode() throws Exception {
        PersonDTO personDTO = personDTOFromJson();
        personDTO.setUfn("UFN4");

        ResponseEntity<String> updateResult = testRestTemplate.exchange("/person/update", HttpMethod.PUT, httpEntity(personDTO), String.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findPersonByUfnWhenValidUFNPassedShouldReturnCorrespondingPerson() throws Exception {
        PersonDTO personDTO = personDTOFromJson();
        personDTO.setUfn("UFN5");

        testRestTemplate.postForEntity("/person/persist", httpEntity(personDTO), String.class);

        ResponseEntity<PersonDTO> result = testRestTemplate.getForEntity("/person/UFN5", PersonDTO.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        PersonDTO foundPerson = result.getBody();
        assertThat(personDTO.getUfn()).isEqualTo(foundPerson.getUfn());
    }

    @Test
    public void findPersonByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() throws Exception {

        ResponseEntity<PersonDTO> result = testRestTemplate.getForEntity("/person/UFN6", PersonDTO.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        PersonDTO foundPerson = result.getBody();
        assertThat(foundPerson.getUfn()).isNull();
    }


    private PersonDTO personDTOFromJson() throws IOException {
        return jsonUtil.personDTOFromJson();
    }

    private HttpEntity<String> httpEntity(PersonDTO personDTO) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(jsonUtil.asJsonString(personDTO), headers);
    }

}