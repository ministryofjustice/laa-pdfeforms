package com.moj.digital.laa.service.person;

import com.moj.digital.laa.entity.person.Person;
import com.moj.digital.laa.exception.common.util.FieldsErrorExtractor;
import com.moj.digital.laa.exception.person.InvalidPersonDataException;
import com.moj.digital.laa.exception.person.PersonNotFoundException;
import com.moj.digital.laa.model.person.PersonDTO;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import com.moj.digital.laa.repository.person.PersonRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
    public void saveWhenThereIsNoInternalErrorPersonShouldBeSave(){
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
}