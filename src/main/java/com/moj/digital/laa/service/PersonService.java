package com.moj.digital.laa.service;

import com.moj.digital.laa.entity.Person;
import com.moj.digital.laa.exception.InvalidPersonDataException;
import com.moj.digital.laa.exception.PersonNotFoundException;
import com.moj.digital.laa.repository.PersonRepository;
import org.springframework.stereotype.Service;

import static com.moj.digital.laa.exception.errormessage.ErrorMessage.*;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        try {
            return personRepository.save(person);
        } catch (Exception e) {
            throw new InvalidPersonDataException(PERSON_PERSIST_ERROR.message(), e);
        }
    }

    public Person findByUfn(String ufn) {
        Person person = personRepository.findByUfn(ufn);
        if (person == null) {
            throw new PersonNotFoundException(String.format(PERSON_NOT_FOUND.message(), ufn));
        }
        return person;
    }

    public void update(Person person) {
        Person existingPerson = findByUfn(person.getUfn());
        person.setId(existingPerson.getId());
        save(person);
    }
}
