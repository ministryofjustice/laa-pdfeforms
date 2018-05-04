package com.moj.digital.laa.integrationtest;

import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
import com.moj.digital.laa.util.JsonTestUtil;
import org.junit.Before;
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
@Import(JsonTestUtil.class)
@AutoConfigureTestDatabase
public class ClientAttendanceIntegrationTest {

    public static final String UFN = "UFN10";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Before
    public void insertClientInDB() throws Exception {
        ClientDTO clientDTO = jsonTestUtil.clientDTOFromJson();
        clientDTO.setUfn(UFN);
        ResponseEntity<String> result = testRestTemplate.postForEntity
                ("/client/register", httpEntity(clientDTO), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        ResponseEntity<ResponseWrapper> result = createAttendance(UFN);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistAttendanceWhenAttendancePertainingToNonExitingClientIsPassedShouldReturnInternalErrorStatusCode() throws Exception {
        ResponseEntity<ResponseWrapper> result = createAttendance("UFN2");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void updateAttendanceWhenTryingToUpdateAnExistingAttendanceShouldReturnCreatedStatusCode() throws Exception {
        ResponseEntity<ResponseWrapper> result = createAttendance(UFN);
        Long attendanceId = result.getBody().getId();

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn(UFN);
        attendanceDTO.setId(attendanceId);

        ResponseEntity<ResponseWrapper> updateResult = testRestTemplate.exchange("/client/attendance/update", HttpMethod.PUT, httpEntity(attendanceDTO), ResponseWrapper.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    /*

    @Test
    public void updateClientWhenTryingToUpdateANonExistingClientShouldReturnNotFoundHttpStatusCode() throws Exception {
        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setUfn("UFN4");

        ResponseEntity<String> updateResult = testRestTemplate.exchange("/client/update", HttpMethod.PUT, httpEntity(clientDTO), String.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findClientByUfnWhenValidUFNPassedShouldReturnCorrespondingClient() throws Exception {
        ClientDTO clientDTO = clientDTOFromJson();
        clientDTO.setUfn("UFN5");

        testRestTemplate.postForEntity("/client/register", httpEntity(clientDTO), String.class);

        ResponseEntity<ClientDTO> result = testRestTemplate.getForEntity("/client/UFN5", ClientDTO.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        ClientDTO foundClient = result.getBody();
        assertThat(clientDTO.getUfn()).isEqualTo(foundClient.getUfn());
    }

    @Test
    public void findClientByUfnWhenInValidUFNPassedShouldReturnNotFoundStatusCode() {

        ResponseEntity<ClientDTO> result = testRestTemplate.getForEntity("/client/UFN6", ClientDTO.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ClientDTO foundClient = result.getBody();
        assertThat(foundClient.getUfn()).isNull();
    }

*/

    private ResponseEntity<ResponseWrapper> createAttendance(String ufn1) throws IOException {
        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn(ufn1);

        return testRestTemplate.postForEntity
                ("/client/attendance/make", httpEntity(attendanceDTO), ResponseWrapper.class);
    }

    private HttpEntity<String> httpEntity(Object object) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(jsonTestUtil.asJsonString(object), headers);
    }

}
