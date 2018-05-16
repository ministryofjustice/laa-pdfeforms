package com.moj.digital.laa.integrationtest;

import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
import com.moj.digital.laa.util.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JsonTestUtil.class)
@AutoConfigureTestDatabase
public class ClientAttendanceNoteIntegrationTest {


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JsonTestUtil jsonTestUtil;

    public void insertClientInDB(String ufn) throws Exception {
        ClientDTO clientDTO = jsonTestUtil.clientDTOFromJson();
        clientDTO.setUfn(ufn);
        ResponseEntity<String> result = testRestTemplate.postForEntity
                ("/client/register", httpEntity(clientDTO), String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistAttendanceNoteWhenInputsAreValidShouldSaveAttendanceNote() throws Exception {
        insertClientInDB("UFN20");
        ResponseEntity<ResponseWrapper> result = createAttendanceNote("UFN20");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistAttendanceNoteWhenAttendanceNotePertainingToNonExitingClientIsPassedShouldReturnInternalErrorStatusCode() throws Exception {
        insertClientInDB("UFN21");
        ResponseEntity<ResponseWrapper> result = createAttendanceNote("abc");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void updateAttendanceNoteWhenTryingToUpdateAnExistingAttendanceNoteShouldReturnCreatedStatusCode() throws Exception {
        insertClientInDB("UFN22");
        ResponseEntity<ResponseWrapper> result = createAttendanceNote("UFN22");
        Long attendanceId = result.getBody().getId();

        AttendanceNoteDTO attendanceNoteDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceNoteDTO.setUfn("UFN22");
        attendanceNoteDTO.setId(attendanceId);

        ResponseEntity<ResponseWrapper> updateResult = testRestTemplate.exchange("/client/attendanceNote/update", HttpMethod.PUT, httpEntity(attendanceNoteDTO), ResponseWrapper.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }


    @Test
    public void updateAttendanceNoteWhenTryingToUpdateANonExistingClientShouldReturnNotFoundHttpStatusCode() throws Exception {
        insertClientInDB("UFN23");

        AttendanceNoteDTO attendanceNoteDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceNoteDTO.setUfn("UFN23");
        attendanceNoteDTO.setId(12L);

        ResponseEntity<ResponseWrapper> updateResult = testRestTemplate.exchange("/client/attendanceNote/update", HttpMethod.PUT, httpEntity(attendanceNoteDTO), ResponseWrapper.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAttendanceNoteNoteByIdWhenAValidIdIsPassedShouldReturnAttendanceNote() throws Exception {
        insertClientInDB("UFN24");
        ResponseEntity<ResponseWrapper> result = createAttendanceNote("UFN24");
        Long attendanceId = result.getBody().getId();

        ResponseEntity<AttendanceNoteDTO> fetchResult = testRestTemplate.getForEntity("/client/attendanceNote/forID/" + attendanceId, AttendanceNoteDTO.class);
        assertThat(fetchResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetchResult.getBody().getId()).isEqualTo(attendanceId);

    }

    @Test
    public void findAttendanceNoteNoteByUfnWhenAValidUFNIsPassedShouldReturnListOfMatchingAttendanceNote() throws Exception {
        insertClientInDB("UFN25");
        ResponseEntity<ResponseWrapper> attendance1 = createAttendanceNote("UFN25");
        ResponseEntity<ResponseWrapper> attendance2 = createAttendanceNote("UFN25");

        ParameterizedTypeReference<List<AttendanceNoteDTO>> paramType = new ParameterizedTypeReference<List<AttendanceNoteDTO>>() {};

        ResponseEntity<List<AttendanceNoteDTO>> fetchResult = testRestTemplate.exchange("/client/attendanceNote/allForUFN/UFN25", HttpMethod.GET, null,paramType);
        assertThat(fetchResult.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AttendanceNoteDTO> attendanceNoteDTOS = fetchResult.getBody();
        assertThat(attendanceNoteDTOS.size()).isEqualTo(2);

        List<AttendanceNoteDTO> sortedAttendanceNoteDTOS = attendanceNoteDTOS.stream().sorted(Comparator.comparing(AttendanceNoteDTO::getId)).collect(Collectors.toList());

        Assertions.assertThat(sortedAttendanceNoteDTOS.size()).isEqualTo(2);
        Assertions.assertThat(sortedAttendanceNoteDTOS.get(0).getId()).isEqualTo(attendance1.getBody().getId());
        Assertions.assertThat(sortedAttendanceNoteDTOS.get(1).getId()).isEqualTo(attendance2.getBody().getId());
    }

    private ResponseEntity<ResponseWrapper> createAttendanceNote(String ufn1) throws IOException {
        AttendanceNoteDTO attendanceNoteDTO = jsonTestUtil.attendanceNoteDTOFromJson();
        attendanceNoteDTO.setUfn(ufn1);

        return testRestTemplate.postForEntity
                ("/client/attendanceNote/make", httpEntity(attendanceNoteDTO), ResponseWrapper.class);
    }

    private HttpEntity<String> httpEntity(Object object) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(jsonTestUtil.asJsonString(object), headers);
    }

}
