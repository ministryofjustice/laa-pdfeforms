package com.moj.digital.laa.repository.client.attendancenote;

import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientAttendanceNoteRepository extends JpaRepository<AttendanceNote,Long> {
    List<AttendanceNote> findByUfn(String ufn);
}
