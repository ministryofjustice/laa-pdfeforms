package com.moj.digital.laa.repository.client.attendance;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientAttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByUfn(String ufn);
}
