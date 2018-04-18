package com.moj.digital.laa.controller.person;

import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.model.person.ResponseWrapper;
import com.moj.digital.laa.service.person.PersonService;
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
@RequestMapping(path = "/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(path = "/persist")
    public ResponseEntity<ResponseWrapper> persistPerson(@Valid @RequestBody PersonDTO person) {
        log.debug("persistPerson invoked with person {} ", person);
        personService.save(person);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Person with UFN %s created", person.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.CREATED);

    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> updatePerson(@Valid @RequestBody PersonDTO person) {
        log.debug("updatePerson invoked with person {} ", person);
        personService.update(person);
        ResponseWrapper responseWrapper = new ResponseWrapper(String.format("Person with UFN %s updated", person.getUfn()));
        return new ResponseEntity<ResponseWrapper>(responseWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{ufn}")
    public ResponseEntity<PersonDTO> findPersonByUfn(@PathVariable String ufn) {
        log.debug("findPersonByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity(personService.findByUfn(ufn), HttpStatus.OK);
    }

    @GetMapping(path = "/containingUfn/{ufn}")
    public ResponseEntity<List<PersonDTO>> findPersonByUfnContaining(@PathVariable String ufn) {
        log.debug("findPersonByUfn invoked with ufn {} ", ufn);
        return new ResponseEntity<List<PersonDTO>>(personService.findByUfnContaining(ufn), HttpStatus.OK);
    }
}
