package com.moj.digital.laa.repository.client.attendance;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.util.JsonTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class ClientAttendanceRepositoryTest {
    private static final String UFN_189 = "UFN189";

    @Autowired
    private JsonTestUtil jsonTestUtil;


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientAttendanceRepository clientAttendanceRepository;

    @Test
    public void findByUfnWhenAValidUFNisPassedShouldReturnListOfMatchingRecords() throws IOException {
        Attendance attendance1 = jsonTestUtil.attendanceFromJson();

        attendance1.setActionText("action1");
        attendance1.setUfn(UFN_189);

        Attendance attendance2 = jsonTestUtil.attendanceFromJson();

        attendance2.setActionText("action2");
        attendance2.setUfn(UFN_189);

        testEntityManager.persistAndFlush(attendance1);
        testEntityManager.persistAndFlush(attendance2);

        List<Attendance> attendanceList = clientAttendanceRepository.findByUfn(UFN_189);
        List<Attendance> sortedAttendanceList = attendanceList.stream().sorted(Comparator.comparing(Attendance::getId)).collect(Collectors.toList());

        assertThat(sortedAttendanceList.size()).isEqualTo(2);
        assertThat(sortedAttendanceList.get(0).getActionText()).isEqualTo("action1");
        assertThat(sortedAttendanceList.get(1).getActionText()).isEqualTo("action2");

    }

    @Before
    public void insertClientFirst() throws IOException {
        Person client = jsonTestUtil.clientFromJson();
        client.setUfn(UFN_189);
        testEntityManager.persistAndFlush(client);
    }

}