package com.example.credit.web.controller;

import com.example.credit.data.Credit;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.CreditStage;
import com.example.credit.data.enums.Sex;
import com.example.credit.service.CreditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@ControllerAdvice
@RequestMapping("/credit/{id}")
@SessionAttributes("credit")
public class CreditController {

	@Autowired
	private CreditService creditService;

	@ModelAttribute("sex")
	public Sex[] addSexEnumToModel(Model model) {

		return Sex.values();

	}
	
	@ModelAttribute("contactTypes")
	public ContactType[] addContactTypesToModel(Model model) {
		
		return ContactType.values();
		
	}

	@GetMapping
	public String getCreditStageView(@PathVariable("id") String creditId, Model model) {

		Credit credit = creditService.getCreditById(creditId);

		if (credit != null) {

			model.addAttribute("credit", credit);

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

	@GetMapping("/profile")
	public String getProfilePage(@PathVariable("id") String creditId, Model model) {

		if (!model.containsAttribute("credit")) {
			return "redirect:/credit/" + creditId;
		}

		return "profile";

	}

}
