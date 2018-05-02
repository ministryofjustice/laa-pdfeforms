package com.moj.digital.laa.controller.client.attendance;


import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
import com.moj.digital.laa.service.client.attendance.ClientAttendanceService;
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
@RequestMapping(path = "/client/attendance")
public class ClientAttendanceController {

    private final ClientAttendanceService clientAttendanceService;

    public ClientAttendanceController(ClientAttendanceService clientAttendanceService) {
        this.clientAttendanceService = clientAttendanceService;
    }

    @PostMapping(path = "/make")
    public ResponseEntity<ResponseWrapper> makeAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        log.debug("makeAttendance invoked with attendance {} ", attendanceDTO);
        clientAttendanceService.save(attendanceDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Attendance created for client with UFN %s", attendanceDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        log.debug("updateAttendance invoked with attendance note {} ", attendanceDTO);
        clientAttendanceService.update(attendanceDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Attendance updated for client with UFN %s", attendanceDTO.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> findAttendanceById(@PathVariable Long id) {
        log.debug("findAttendanceById invoked with id {} ", id);
        return new ResponseEntity(clientAttendanceService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/{ufn}")
    public ResponseEntity<List<AttendanceDTO>> findAttendancesByUfn(@PathVariable String ufn) {
        log.debug("findAttendanceByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity<List<AttendanceDTO>>(clientAttendanceService.findByUfn(ufn), HttpStatus.OK);
    }

}
