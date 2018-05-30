package com.moj.digital.laa.controller.client.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "ui")
public class HelloController {

    @Value("${assetPath}")
    private String assetPath;

    @GetMapping(path = "/")
    public ModelAndView index() {
        Map<String, String> params = new HashMap<>();
        params.put("assetPath", assetPath);
        return new ModelAndView("index", params);
    }
}
