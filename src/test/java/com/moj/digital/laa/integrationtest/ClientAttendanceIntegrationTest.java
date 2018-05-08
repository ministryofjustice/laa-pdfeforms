package com.moj.digital.laa.integrationtest;

import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
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
public class ClientAttendanceIntegrationTest {


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
    public void persistAttendanceWhenInputsAreValidShouldSaveAttendance() throws Exception {
        insertClientInDB("UFN14");
        ResponseEntity<ResponseWrapper> result = createAttendance("UFN14");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void persistAttendanceWhenAttendancePertainingToNonExitingClientIsPassedShouldReturnInternalErrorStatusCode() throws Exception {
        insertClientInDB("UFN15");
        ResponseEntity<ResponseWrapper> result = createAttendance("abc");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void updateAttendanceWhenTryingToUpdateAnExistingAttendanceShouldReturnCreatedStatusCode() throws Exception {
        insertClientInDB("UFN16");
        ResponseEntity<ResponseWrapper> result = createAttendance("UFN16");
        Long attendanceId = result.getBody().getId();

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn("UFN16");
        attendanceDTO.setId(attendanceId);

        ResponseEntity<ResponseWrapper> updateResult = testRestTemplate.exchange("/client/attendance/update", HttpMethod.PUT, httpEntity(attendanceDTO), ResponseWrapper.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }


    @Test
    public void updateAttendanceWhenTryingToUpdateANonExistingClientShouldReturnNotFoundHttpStatusCode() throws Exception {
        insertClientInDB("UFN17");

        AttendanceDTO attendanceDTO = jsonTestUtil.attendanceDTOFromJson();
        attendanceDTO.setUfn("UFN17");
        attendanceDTO.setId(12L);

        ResponseEntity<ResponseWrapper> updateResult = testRestTemplate.exchange("/client/attendance/update", HttpMethod.PUT, httpEntity(attendanceDTO), ResponseWrapper.class);
        assertThat(updateResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAttendanceNoteByIdWhenAValidIdIsPassedShouldReturnAttendance() throws Exception {
        insertClientInDB("UFN18");
        ResponseEntity<ResponseWrapper> result = createAttendance("UFN18");
        Long attendanceId = result.getBody().getId();

        ResponseEntity<AttendanceNoteDTO> fetchResult = testRestTemplate.getForEntity("/client/attendance/forID/" + attendanceId, AttendanceNoteDTO.class);
        assertThat(fetchResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetchResult.getBody().getId()).isEqualTo(attendanceId);

    }

    @Test
    public void findAttendanceNoteByUfnWhenAValidUFNIsPassedShouldReturnListOfMatchingAttendance() throws Exception {
        insertClientInDB("UFN19");
        ResponseEntity<ResponseWrapper> attendance1 = createAttendance("UFN19");
        ResponseEntity<ResponseWrapper> attendance2 = createAttendance("UFN19");

        ParameterizedTypeReference<List<AttendanceDTO>> paramType = new ParameterizedTypeReference<List<AttendanceDTO>>() {};

        ResponseEntity<List<AttendanceDTO>> fetchResult = testRestTemplate.exchange("/client/attendance/allForUFN/UFN19", HttpMethod.GET, null,paramType);
        assertThat(fetchResult.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AttendanceDTO> attendanceDTOS = fetchResult.getBody();
        assertThat(attendanceDTOS.size()).isEqualTo(2);

        List<AttendanceDTO> sortedAttendanceDTOS = attendanceDTOS.stream().sorted(Comparator.comparing(AttendanceDTO::getId)).collect(Collectors.toList());

        Assertions.assertThat(sortedAttendanceDTOS.size()).isEqualTo(2);
        Assertions.assertThat(sortedAttendanceDTOS.get(0).getId()).isEqualTo(attendance1.getBody().getId());
        Assertions.assertThat(sortedAttendanceDTOS.get(1).getId()).isEqualTo(attendance2.getBody().getId());
    }

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
