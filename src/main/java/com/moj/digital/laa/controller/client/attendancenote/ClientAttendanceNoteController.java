package com.moj.digital.laa.controller.client.attendancenote;


import com.moj.digital.laa.model.client.ClientDTO;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
import com.moj.digital.laa.service.client.attendancenote.ClientAttendanceNoteService;
import com.moj.digital.laa.service.client.registration.ClientRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(path = "/client/attendanceNote")
public class ClientAttendanceNoteController {

    private final ClientAttendanceNoteService clientAttendanceNoteService;

    public ClientAttendanceNoteController(ClientAttendanceNoteService clientAttendanceNoteService) {
        this.clientAttendanceNoteService = clientAttendanceNoteService;
    }

    @PostMapping(path = "/make")
    public ResponseEntity<ResponseWrapper> makeAttendanceNote(@Valid @RequestBody AttendanceNoteDTO attendanceNoteDTO) {
        log.debug("makeAttendanceNote invoked with attendance note {} ", attendanceNoteDTO);
        clientAttendanceNoteService.save(attendanceNoteDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Attendance note with with client UFN %s made", attendanceNoteDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateAttendanceNote(@Valid @RequestBody AttendanceNoteDTO attendanceNoteDTO) {
        log.debug("updateAttendanceNote invoked with attendance note {} ", attendanceNoteDTO);
        clientAttendanceNoteService.update(attendanceNoteDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Attendance note with client UFN %s updated", attendanceNoteDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> findAttendanceNoteById(@PathVariable Long id) {
        log.debug("findAttendanceNoteById invoked with id {} ", id);
        return new ResponseEntity(clientAttendanceNoteService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/containingUfn/{ufn}")
    public ResponseEntity<List<AttendanceNoteDTO>> findAttendanceNoteByUfn(@PathVariable String ufn) {
        log.debug("findClientByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity<List<AttendanceNoteDTO>>(clientAttendanceNoteService.findByUfnContaining(ufn), HttpStatus.OK);
    }

}
