package com.moj.digital.laa.controller.client.attendancenote;


import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
import com.moj.digital.laa.service.client.attendancenote.ClientAttendanceNoteService;
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
        Long savedId = clientAttendanceNoteService.save(attendanceNoteDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(savedId, String.format("Attendance note created for client with UFN %s", attendanceNoteDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateAttendanceNote(@Valid @RequestBody AttendanceNoteDTO attendanceNoteDTO) {
        log.debug("updateAttendance invoked with attendance note {} ", attendanceNoteDTO);
        Long updatedId = clientAttendanceNoteService.update(attendanceNoteDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(updatedId, String.format("Attendance note updated for client with UFN %s", attendanceNoteDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "forID/{id}")
    public ResponseEntity<AttendanceNoteDTO> findAttendanceNoteById(@PathVariable Long id) {
        log.debug("findAttendanceById invoked with id {} ", id);
        return new ResponseEntity(clientAttendanceNoteService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "allForUFN/{ufn}")
    public ResponseEntity<List<AttendanceNoteDTO>> findAttendanceNoteByUfn(@PathVariable String ufn) {
        log.debug("findAttendanceByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity<List<AttendanceNoteDTO>>(clientAttendanceNoteService.findByUfn(ufn), HttpStatus.OK);
    }

}
