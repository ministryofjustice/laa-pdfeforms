package com.moj.digital.laa.controller.client.ui.attendancenote;

import com.moj.digital.laa.model.client.attendance.AttendanceDTO;
import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
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
@RequestMapping(path = "/ui/client/attendanceNote")
public class AttendanceNoteViewController {

    @Value("${assetPath}")
    private String assetPath;

    private ClientAttendanceNoteService clientAttendanceNoteService;

    public AttendanceNoteViewController(ClientAttendanceNoteService clientAttendanceNoteService) {
        this.clientAttendanceNoteService = clientAttendanceNoteService;
    }

    @GetMapping(path = "/search")
    public String search(Model model) {
        model.addAttribute("assetPath", assetPath);
        return "/client/attendanceNote/search";
    }

    @PostMapping(path = "/index")
    public ModelAndView index(@RequestParam String ufn) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);
        List<AttendanceNoteDTO> attendanceNotes = clientAttendanceNoteService.findByUfn(ufn);

        if(attendanceNotes.isEmpty()){
            params.put("no-records-found","no-records-found");
        }
        params.put("attendanceNotes", attendanceNotes);
        return new ModelAndView("/client/attendanceNote/index", params);
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);

        AttendanceNoteDTO attendanceNoteDTO = clientAttendanceNoteService.findById(id);

        if (attendanceNoteDTO == null) {
            params.put("no-data-found", "no-data-found");
        }
        params.put("attendanceNote", attendanceNoteDTO);

        return new ModelAndView("/client/attendanceNote/edit", params);

    }

    @PostMapping(path = "/update")
    public ModelAndView update(@ModelAttribute AttendanceNoteDTO attendanceNoteDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);

        try{
            clientAttendanceNoteService.save(attendanceNoteDTO);
        }catch (Exception e){
            params.put("Exception", "Exception");
        }

        return new ModelAndView("/client/attendanceNote/search", params);

    }


}
