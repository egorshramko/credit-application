package com.example.credit.web.controller;

import com.example.credit.data.Credit;
import com.example.credit.data.enums.CreditStage;
import com.example.credit.service.CreditService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@ControllerAdvice
@RequestMapping("/credit/{id}")
public class CreditController {

	@Autowired
	private CreditService creditService;

	@GetMapping
	public String getCreditStageView(@PathVariable("id") String creditId) {

		Credit credit = creditService.getCreditById(creditId);

		if (credit != null) {

			if (credit.getStage() == CreditStage.CREDIT_FORM) {
				return "redirect:/credit/" + credit.getId() + "/profile";
			} else if (credit.getStage() == CreditStage.CREDIT_APPLICATION) {
				return "redirect:/credit/" + credit.getId() + "/app";
			} else if (credit.getStage() == CreditStage.AGREEMENT_SIGNING) {
				return "redirect:/credit/" + credit.getId() + "/agreement";
			} else {
				return "redirect:/credit/" + credit.getId() + "/reject";
			}

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit application not found");
		}

	}
	
	@GetMapping("/")
	public String redirectToCreditStageView(@PathVariable("id") String creditId) {
		return "redirect:/credit/" + creditId;
	}

}
