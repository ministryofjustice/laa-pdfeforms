package com.moj.digital.laa.controller.person;

import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.service.person.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity<String> persistPerson(@Valid @RequestBody PersonDTO person) {
        log.debug("persistPerson invoked with person {} ", person);
        personService.save(person);
        return new ResponseEntity(String.format("Person with UFN %s created", person.getUfn()), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updatePerson(@Valid @RequestBody PersonDTO person) {
        log.debug("updatePerson invoked with person {} ", person);
        personService.update(person);
        return new ResponseEntity(String.format("Person with UFN %s updated", person.getUfn()), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{ufn}")
    public ResponseEntity<List<PersonDTO>> findPersonByUfn(@PathVariable String ufn) {
        log.debug("findPersonByUfn invoked with ufn {} ", ufn);
        PersonDTO personDTO = personService.findByUfn(ufn);
        List<PersonDTO> personDTOS = new ArrayList<>();
        personDTOS.add(personDTO);
        return new ResponseEntity(personDTOS, HttpStatus.OK);
    }
}
