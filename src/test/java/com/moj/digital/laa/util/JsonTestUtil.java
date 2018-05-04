package com.moj.digital.laa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moj.digital.laa.common.config.MapperConfiguration;
import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;

@TestComponent
@Import(MapperConfiguration.class)
public class JsonTestUtil {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${test.json.files.client}")
    private String testJsonFileClient;

    @Value("${test.json.files.attendance}")
    private String testJsonFileAttendance;

    @Value("${test.json.files.attendanceNote}")
    private String testJsonFileAttendanceNote;

    public ClientDTO clientDTOFromJson() throws IOException {
        InputStream is = JsonTestUtil.class.getResourceAsStream(testJsonFileClient + "client.json");
        ClientDTO clientDTO = objectMapper.readValue(is, ClientDTO.class);
        return clientDTO;
    }

    public Person clientFromJson() throws IOException {
        return modelMapper.map(clientDTOFromJson(), Person.class);
    }

    public String asJsonString(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public AttendanceDTO attendanceDTOFromJson() throws IOException {
        InputStream is = JsonTestUtil.class.getResourceAsStream(testJsonFileAttendance + "attendance.json");
        return objectMapper.readValue(is, AttendanceDTO.class);
    }

    public Attendance attendanceFromJson() throws IOException {
        return modelMapper.map(attendanceDTOFromJson(),Attendance.class);
    }

    public AttendanceNoteDTO attendanceNoteDTOFromJson() throws IOException {
        InputStream is = JsonTestUtil.class.getResourceAsStream(testJsonFileAttendanceNote + "attendanceNote.json");
        return objectMapper.readValue(is, AttendanceNoteDTO.class);
    }

    public AttendanceNote attendanceNoteFromJson() throws IOException {
        return modelMapper.map(attendanceDTOFromJson(),AttendanceNote.class);
    }
}
