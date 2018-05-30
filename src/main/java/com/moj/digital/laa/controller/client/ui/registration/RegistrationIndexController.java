package com.moj.digital.laa.controller.client.ui.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(path = "/ui/registration")
public class RegistrationIndexController {
    @Value("${assetPath}")
    private String assetPath;

    @GetMapping(path = "/index")
    public ModelAndView index() {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);
        List<String> data = new ArrayList<>();
        data.add("smt1");
        data.add("smt2");
        data.add("smt3");
        params.put("data",data);
        return new ModelAndView("/client/registration/index", params);
    }
}
