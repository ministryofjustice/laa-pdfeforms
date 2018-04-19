package com.moj.digital.laa.service.person;

import com.moj.digital.laa.entity.person.Person;
import com.moj.digital.laa.exception.person.InvalidPersonDataException;
import com.moj.digital.laa.exception.person.PersonNotFoundException;
import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.repository.person.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.PERSON_NOT_FOUND;
import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.PERSON_PERSIST_ERROR;

@Service
@Slf4j
public class PersonService {

    private PersonRepository personRepository;
    private ModelMapper modelMapper;

    public PersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    public void save(PersonDTO personDTO) {
        log.debug("save person called with person {}", personDTO);
        try {
            Person person = modelMapper.map(personDTO, Person.class);

            person.getDisabilities().stream().forEach(disability -> {
                disability.setPersonID(person);
            });

            log.debug("Person to be updated {}",person);
            personRepository.save(person);
        } catch (Exception e) {
            log.error("Could not persist person details because of exception {}", e);
            throw new InvalidPersonDataException(PERSON_PERSIST_ERROR.message() + e.getMessage(), e);
        }
    }

    public PersonDTO findByUfn(String ufn) {
        log.debug("Find Person by UFN called with UFN {}", ufn);
        Person person = personRepository.findByUfn(ufn);
        if (person == null) {
            log.warn("Could not identify a person with the provided UFN {}", ufn);
            throw new PersonNotFoundException(String.format(PERSON_NOT_FOUND.message(), ufn));
        }
        return modelMapper.map(person, PersonDTO.class);
    }

    public List<PersonDTO> findByUfnContaining(String ufn) {
        log.debug("Find by UFN containing called with UFN {}", ufn);
        List<Person> personList = personRepository.findByUfnContaining(ufn);

        Type targetType = new TypeToken<List<PersonDTO>>() {
        }.getType();

        return modelMapper.map(personList, targetType);
    }

    public void update(PersonDTO personDTO) {
        log.debug("update person called with person {}", personDTO);
        PersonDTO existingPersonDTO = findByUfn(personDTO.getUfn());
        personDTO.setId(existingPersonDTO.getId());
        save(personDTO);
    }
}
