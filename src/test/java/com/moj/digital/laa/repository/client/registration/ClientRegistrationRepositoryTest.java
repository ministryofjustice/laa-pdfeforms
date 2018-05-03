package com.moj.digital.laa.repository.client.registration;

import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.util.JsonTestUtil;
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
public class ClientRegistrationRepositoryTest {

    public static final String UFN_189 = "UFN189";

    @Autowired
    private JsonTestUtil jsonTestUtil;


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnPerson() throws IOException {
        Person client = jsonTestUtil.clientFromJson();
        client.setUfn(UFN_189);

        testEntityManager.persistAndFlush(client);

        Person retrievedClientObjectFromDB = clientRegistrationRepository.findByUfn(UFN_189);
        assertThat(client.getUfn()).isEqualTo(retrievedClientObjectFromDB.getUfn());
    }
}