package com.moj.digital.laa.service.person;

import com.moj.digital.laa.entity.person.Person;
import com.moj.digital.laa.exception.person.InvalidPersonDataException;
import com.moj.digital.laa.exception.person.PersonNotFoundException;
import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.repository.person.PersonRepository;
import com.moj.digital.laa.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Import({ModelMapper.class})
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Before
    public void setup() {
        personService = new PersonService(personRepository, new ModelMapper());
    }

    @Test(expected = InvalidPersonDataException.class)
    public void saveWhenPersonWhenServiceFailureOccursShouldThrowException() {
        when(personRepository.save(any(Person.class))).thenThrow(new RuntimeException(""));
        personService.save(new PersonDTO());
    }

    @Test
    public void saveWhenThereIsNoInternalErrorPersonShouldBeSaved() {
        PersonDTO personDTO = new PersonDTO();
        personService.save(personDTO);
    }

    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnPerson() {
        when(personRepository.findByUfn("UFN1")).thenReturn(new Person());
        personService.findByUfn("UFN1");
    }

    @Test(expected = PersonNotFoundException.class)
    public void findByUfnWhenAnInValidUFNIsSentShouldThrowAnException() {
        when(personRepository.findByUfn("UFN1")).thenReturn(null);
        personService.findByUfn("UFN1");
    }

    @Test(expected = InvalidPersonDataException.class)
    public void updateWhenPersonWhenServiceFailureOccursShouldThrowException() {
        when(personRepository.findByUfn("UFN1")).thenReturn(new Person());
        when(personRepository.save(any(Person.class))).thenThrow(new RuntimeException(""));

        PersonDTO personDTOtoBeUpdated = new PersonDTO();
        personDTOtoBeUpdated.setUfn("UFN1");

        personService.update(personDTOtoBeUpdated);
    }

    @Test
    public void updateWhenThereIsNoInternalErrorPersonShouldBeSaved() {
        when(personRepository.findByUfn("UFN1")).thenReturn(new Person());

        PersonDTO personDTOtoBeUpdated = new PersonDTO();
        personDTOtoBeUpdated.setUfn("UFN1");

        personService.update(personDTOtoBeUpdated);
    }
}