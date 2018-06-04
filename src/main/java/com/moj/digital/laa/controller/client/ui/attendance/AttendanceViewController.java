package com.moj.digital.laa.controller.client.ui.attendance;

import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.service.client.attendance.ClientAttendanceService;
import com.moj.digital.laa.service.client.attendancenote.ClientAttendanceNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(path = "/ui/client/attendance")
public class AttendanceViewController {

    @Value("${assetPath}")
    private String assetPath;

    private ClientAttendanceService clientAttendanceService;

    public AttendanceViewController(ClientAttendanceService clientAttendanceService) {
        this.clientAttendanceService = clientAttendanceService;
    }

    @GetMapping(path = "/search")
    public String search(Model model) {
        model.addAttribute("assetPath", assetPath);
        return "/client/attendance/search";
    }

    @PostMapping(path = "/index")
    public ModelAndView index(@RequestParam String ufn) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);
        List<AttendanceDTO> attendanceNotes = clientAttendanceService.findByUfn(ufn);

        if (attendanceNotes.isEmpty()) {
            params.put("no-records-found", "no-records-found");
        }
        params.put("attendance", attendanceNotes);
        return new ModelAndView("/client/attendance/index", params);
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);

        AttendanceDTO attendanceDTO = clientAttendanceService.findById(id);

        if (attendanceDTO == null) {
            params.put("no-data-found", "no-data-found");
        }
        params.put("attendance", attendanceDTO);

        return new ModelAndView("/client/attendance/edit", params);

    }

    @PostMapping(path = "/update")
    public ModelAndView update(@ModelAttribute AttendanceDTO attendance) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);

        try{
            clientAttendanceService.save(attendance);
        }catch (Exception e){
            params.put("Exception", "Exception");
        }

        return new ModelAndView("/client/attendance/search", params);

    }


}
