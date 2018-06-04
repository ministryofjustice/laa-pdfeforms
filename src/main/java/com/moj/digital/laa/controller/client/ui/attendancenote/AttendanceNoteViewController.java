package com.moj.digital.laa.controller.client.ui.attendancenote;

import com.moj.digital.laa.model.client.attendancenote.AttendanceNoteDTO;
import com.moj.digital.laa.service.client.attendancenote.ClientAttendanceNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

}
