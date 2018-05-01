package com.moj.digital.laa.service.client.attendancenote;

import com.moj.digital.laa.entity.client.attendancenote.AttendanceNote;
import com.moj.digital.laa.exception.client.InvalidClientRegistrationDataException;
import com.moj.digital.laa.exception.client.attendance.AttendanceNoteNotFoundException;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.repository.client.attendancenote.ClientAttendanceNoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.ATTENDANCE_NOTE_NOT_FOUND;
import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.CLIENT_PERSIST_ERROR;

@Service
@Slf4j
public class ClientAttendanceNoteService {
    private ClientAttendanceNoteRepository clientAttendanceNoteRepository;
    private ModelMapper modelMapper;

    public ClientAttendanceNoteService(ClientAttendanceNoteRepository clientAttendanceNoteRepository, ModelMapper modelMapper) {
        this.clientAttendanceNoteRepository = clientAttendanceNoteRepository;
        this.modelMapper = modelMapper;
    }

    public void save(AttendanceNoteDTO attendanceNoteDTO) {
        log.debug("save attendance note called with client ufn {}", attendanceNoteDTO.getUfn());
        try {
            AttendanceNote attendanceNote = modelMapper.map(attendanceNoteDTO, AttendanceNote.class);
            log.debug("Attendance note to be updated {}", attendanceNote);
            clientAttendanceNoteRepository.save(attendanceNote);
        } catch (Exception e) {
            log.error("Could not persist attendance note details because of exception {}", e);
            throw new InvalidClientRegistrationDataException(CLIENT_PERSIST_ERROR.message() + e.getMessage(), e);
        }
    }

    public List<AttendanceNoteDTO> findByUfnContaining(String ufn) {
        log.debug("Find by UFN containing called with UFN {}", ufn);
        List<AttendanceNote> attendanceNoteList = clientAttendanceNoteRepository.findByUfn(ufn);

        Type targetType = new TypeToken<List<AttendanceNoteDTO>>() {
        }.getType();

        return modelMapper.map(attendanceNoteList, targetType);
    }

    public AttendanceNoteDTO findById(Long id) {
        log.debug("Find attendance note by id called with id {}", id);
        Optional<AttendanceNote> attendanceNote = clientAttendanceNoteRepository.findById(id);
        if (!attendanceNote.isPresent()) {
            log.warn("Could not identify attendance note with id {}", id);
            throw new AttendanceNoteNotFoundException(String.format(ATTENDANCE_NOTE_NOT_FOUND.message(), id));
        }
        return modelMapper.map(attendanceNote.get(), AttendanceNoteDTO.class);
    }

    public void update(AttendanceNoteDTO attendanceNoteDTO) {
        log.debug("update attendance note called with attendance note {}", attendanceNoteDTO);
        AttendanceNoteDTO existingAttendanceNoteDTO = findById(attendanceNoteDTO.getId());
        attendanceNoteDTO.setId(existingAttendanceNoteDTO.getId());
        save(attendanceNoteDTO);
    }

}
