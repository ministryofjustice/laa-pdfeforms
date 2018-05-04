package com.moj.digital.laa.controller.client.registration;

import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.model.common.ResponseWrapper;
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
@RequestMapping(path = "/client")
public class ClientRegistrationController {

    private ClientRegistrationService clientRegistrationService;

    public ClientRegistrationController(ClientRegistrationService clientRegistrationService) {
        this.clientRegistrationService = clientRegistrationService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseWrapper> registerClient(@Valid @RequestBody ClientDTO client) {
        log.debug("registerClient invoked with client {} ", client);
        Long savedClientId = clientRegistrationService.save(client);
        ResponseWrapper responseWrapper = new ResponseWrapper(savedClientId, String.format("Client with UFN %s registered", client.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.CREATED);

    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updateClientRegistration(@Valid @RequestBody ClientDTO client) {
        log.debug("updateClientRegistration invoked with client {} ", client);
        Long updatedClientId = clientRegistrationService.update(client);
        ResponseWrapper responseWrapper = new ResponseWrapper(updatedClientId, String.format("Client with UFN %s updated", client.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{ufn}")
    public ResponseEntity<ClientDTO> findClientByUfn(@PathVariable String ufn) {
        log.debug("findClientByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity(clientRegistrationService.findByUfn(ufn), HttpStatus.OK);
    }

    @GetMapping(path = "/containingUfn/{ufn}")
    public ResponseEntity<List<ClientDTO>> findClientByUfnContaining(@PathVariable String ufn) {
        log.debug("findClientByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity<List<ClientDTO>>(clientRegistrationService.findClientByUfnContaining(ufn), HttpStatus.OK);
    }
}
