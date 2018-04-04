package com.moj.digital.laa.controller.person;

import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.service.person.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(path = "/persist")
    public ResponseEntity<String> persistPerson(@Valid @RequestBody PersonDTO person) {
        log.debug("persistPerson invoked with person {} ",person);
        personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updatePerson(@RequestBody PersonDTO person) {
        log.debug("updatePerson invoked with person {} ",person);
        personService.update(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/{ufn}")
    public PersonDTO findPersonByUfn(@PathVariable String ufn) {
        log.debug("findPersonByUfn invoked with ufn {} ",ufn);
        return personService.findByUfn(ufn);
    }
}
