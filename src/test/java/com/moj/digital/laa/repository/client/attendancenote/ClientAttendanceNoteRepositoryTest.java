package com.moj.digital.laa.repository.client.attendancenote;

import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
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

public class ClientAttendanceNoteRepositoryTest {
    private static final String UFN_189 = "UFN189";

    @Autowired
    private JsonTestUtil jsonTestUtil;


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ClientAttendanceNoteRepository clientAttendanceNoteRepository;

    @Before
    public void insertClientFirst() throws IOException {
        Person client = jsonTestUtil.clientFromJson();
        client.setUfn(UFN_189);
        testEntityManager.persistAndFlush(client);
    }

    @Test
    public void findByUfnWhenAValidUFNisPassedShouldReturnListOfMatchingRecords() throws IOException {
        AttendanceNote attendanceNote1 = jsonTestUtil.attendanceNoteFromJson();

        attendanceNote1.setAdvoc("advoc1");
        attendanceNote1.setUfn(UFN_189);

        AttendanceNote attendanceNote2 = jsonTestUtil.attendanceNoteFromJson();

        attendanceNote2.setAdvoc("advoc2");
        attendanceNote2.setUfn(UFN_189);

        testEntityManager.persistAndFlush(attendanceNote1);
        testEntityManager.persistAndFlush(attendanceNote2);

        List<AttendanceNote> attendanceNoteList = clientAttendanceNoteRepository.findByUfn(UFN_189);
        List<AttendanceNote> sortedAttendanceNoteList = attendanceNoteList.stream().sorted(Comparator.comparing(AttendanceNote::getId)).collect(Collectors.toList());

        assertThat(sortedAttendanceNoteList.size()).isEqualTo(2);
        assertThat(sortedAttendanceNoteList.get(0).getAdvoc()).isEqualTo("advoc1");
        assertThat(sortedAttendanceNoteList.get(1).getAdvoc()).isEqualTo("advoc2");

    }

}