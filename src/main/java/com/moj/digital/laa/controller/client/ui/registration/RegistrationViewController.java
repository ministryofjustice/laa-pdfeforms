package com.moj.digital.laa.controller.client.ui.registration;

import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.service.client.registration.ClientRegistrationService;
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
@RequestMapping(path = "/ui/client/registration")
public class RegistrationViewController {
    @Value("${assetPath}")
    private String assetPath;

    private ClientRegistrationService clientRegistrationService;

    public RegistrationViewController(ClientRegistrationService clientRegistrationService) {
        this.clientRegistrationService = clientRegistrationService;
    }

    @GetMapping(path = "/search")
    public String search(Model model) {
        model.addAttribute("assetPath", assetPath);
        return "/client/registration/search";
    }

    @PostMapping(path = "/index")
    public ModelAndView index(@RequestParam String ufn) {
        Map<String, Object> params = new HashMap<>();
        params.put("assetPath", assetPath);
        List<ClientDTO> clients = clientRegistrationService.findClientByUfnContaining(ufn);

        if(clients.isEmpty()){
            params.put("no-records-found","no-records-found");
        }
        params.put("client", clients);
        return new ModelAndView("/client/registration/index", params);
    }
}
