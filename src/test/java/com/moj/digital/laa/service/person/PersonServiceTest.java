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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        Person person = new Person();
        person.setUfn("UFN1");

        when(personRepository.findByUfn("UFN1")).thenReturn(person);
        PersonDTO personDTO = personService.findByUfn("UFN1");
        assertThat(personDTO.getUfn()).isEqualTo(person.getUfn());
    }

    @Test
    public void findByUfnContainingWhenAValidUFNIsSentShouldReturnMatchingPersons() {
        List<Person> listOfPersons = new ArrayList<>();

        Person person1 = new Person();
        person1.setUfn("UFN1");

        Person person2 = new Person();
        person2.setUfn("UFN2");

        listOfPersons.add(person1);
        listOfPersons.add(person2);

        when(personRepository.findByUfnContaining("UFN")).thenReturn(listOfPersons);
        List<PersonDTO> personDTOs = personService.findByUfnContaining("UFN");

        assertThat(listOfPersons.size()).isEqualTo(personDTOs.size());
        assertThat(listOfPersons.get(0).getUfn()).isEqualTo(personDTOs.get(0).getUfn());
        assertThat(listOfPersons.get(1).getUfn()).isEqualTo(personDTOs.get(1).getUfn());
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