package com.moj.digital.laa.controller;

import com.moj.digital.laa.entity.Person;
import com.moj.digital.laa.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(path = "/persist")
    public ResponseEntity<String> persistPerson(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        personService.update(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/{ufn}")
    public Person findPersonByUfn(@PathVariable String ufn) {
        Person person = personService.findByUfn(ufn);
        return person;
    }
}
