package com.moj.digital.laa.repository.person;

import com.moj.digital.laa.entity.person.Person;
import com.moj.digital.laa.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class PersonRepositoryTest {

    public static final String UFN_189 = "UFN189";

    @Autowired
    private JsonUtil jsonUtil;


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnPerson() throws IOException {
        Person person = jsonUtil.personFromJson();
        person.setUfn(UFN_189);

        testEntityManager.persistAndFlush(person);

        Person retrievedPersonObjectFromDB = personRepository.findByUfn(UFN_189);
        assertThat(person.getUfn()).isEqualTo(retrievedPersonObjectFromDB.getUfn());
    }
}