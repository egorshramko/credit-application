package com.example.credit.web.controller;

import com.example.credit.data.Credit;
import com.example.credit.service.CreditService;
import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
public class HomeController {

	@Autowired
	private CreditService creditService;

	@ModelAttribute("openedCredits")
	public Iterable<Credit> addOpenedCredits(Model model) {

		return creditService.getActiveCredits();

	}

	@GetMapping("/")
	public String getHomePage() {

		return "home";
	}

	// Оформление кредита для нового клиента
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

		Credit credit = creditService.createCreditForNewClient(clientDto);

		return "redirect:/credit/" + credit.getId();
	}

	// Оформление кредита для существующего клиента
	@PostMapping("/apply-loan")
	public String openExistClientCreditApp(@RequestParam String id) {
		log.info("Calling /apply-loan");
		log.info("Request data:");
		log.info("id: " + id);

		try {
			Credit credit = creditService.createCreditForExistClient(id);
			return "redirect:/credit/" + credit.getId();
		} catch (RuntimeException err) {
			err.printStackTrace();
		}

		return "redirect:/";

	}

}
