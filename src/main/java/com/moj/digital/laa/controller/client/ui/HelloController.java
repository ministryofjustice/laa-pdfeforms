package com.moj.digital.laa.controller.client.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping()
public class HelloController {

    @Value("${assetPath}")
    private String assetPath;

    @GetMapping(path = "/")
    public String index() {
        return "redirect:/ui/registration/index";
    }
}
