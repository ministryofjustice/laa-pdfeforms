package com.moj.digital.laa.service.client.attendance;

import com.moj.digital.laa.entity.client.attendance.Attendance;
import com.moj.digital.laa.exception.client.attendance.ClientAttendanceNotFoundException;
import com.moj.digital.laa.exception.client.attendance.InvalidClientAttendanceDataException;
import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.repository.client.attendance.ClientAttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.ATTENDANCE_NOT_FOUND;
import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.ATTENDANCE_PERSIST_ERROR;

@Service
@Slf4j
public class ClientAttendanceService {
    private ClientAttendanceRepository clientAttendanceRepository;
    private ModelMapper modelMapper;

    public ClientAttendanceService(ClientAttendanceRepository clientAttendanceRepository, ModelMapper modelMapper) {
        this.clientAttendanceRepository = clientAttendanceRepository;
        this.modelMapper = modelMapper;
    }

    public Long save(AttendanceDTO attendanceDTO) {
        log.debug("save attendance called with {}", attendanceDTO);
        try {
            Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
            Attendance savedAttendance = clientAttendanceRepository.save(attendance);
            return savedAttendance.getId();
        } catch (Exception e) {
            log.error("Could not persist attendance details because of exception {}", e.getMessage());
            throw new InvalidClientAttendanceDataException(ATTENDANCE_PERSIST_ERROR.message() + e.getMessage(), e);
        }
    }

    public List<AttendanceDTO> findByUfn(String ufn) {
        log.debug("findByUfn called with UFN {}", ufn);
        List<Attendance> attendanceList = clientAttendanceRepository.findByUfn(ufn);

        Type targetType = new TypeToken<List<AttendanceDTO>>() {
        }.getType();

        return modelMapper.map(attendanceList, targetType);
    }

    public AttendanceDTO findById(Long id) {
        log.debug("findById called with id {}", id);
        Optional<Attendance> attendanceNote = clientAttendanceRepository.findById(id);
        if (!attendanceNote.isPresent()) {
            log.warn("Could not identify attendance with id {}", id);
            throw new ClientAttendanceNotFoundException(String.format(ATTENDANCE_NOT_FOUND.message(), id));
        }
        return modelMapper.map(attendanceNote.get(), AttendanceDTO.class);
    }

    public Long update(AttendanceDTO attendanceDTO) {
        log.debug("update attendance called with attendance note {}", attendanceDTO);
        AttendanceDTO existingAttendanceDTO = findById(attendanceDTO.getId());
        attendanceDTO.setId(existingAttendanceDTO.getId());
        return save(attendanceDTO);
    }

}
