package com.example.credit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/credit")
public class CreditPageController {

    @GetMapping
    public String getApplicationPage() {
        return "application";
    }

}
