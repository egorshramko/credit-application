package com.example.credit.web.controller;

import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {

        return "home";
    }

    //Оформление кредита для нового клиента
    @PostMapping("/create-client")
    public String openNewClientCreditApp(@ModelAttribute ClientDto clientDto) {
        log.info("Calling /create-client");
        log.info("Request data:");
        log.info("id: " + clientDto.getId());
        log.info("lastname: " + clientDto.getLastname());
        log.info("firstname: " + clientDto.getFirstname());
        log.info("middlename: " + clientDto.getMiddlename());
        log.info("birthdate: " + clientDto.getBirthdate());
        log.info("series: " + clientDto.getPassportSeries());
        log.info("number: " + clientDto.getPassportNumber());



        return "redirect:/credit";
    }

    //Оформление кредита для существующего клиента
    @PostMapping("/apply-loan")
    public String openExistClientCreditApp(@RequestParam String id) {
        log.info("Calling /apply-loan");
        log.info("Request data:");
        log.info("id: " + id);

        return "redirect:/credit";
    }


}
