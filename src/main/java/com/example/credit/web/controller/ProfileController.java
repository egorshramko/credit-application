package com.example.credit.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.credit.data.ClientProfile;
import com.example.credit.data.Credit;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.Sex;
import com.example.credit.service.CreditService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/credit/{id}/profile")
@SessionAttributes("profile")
public class ProfileController {

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
	
	@ModelAttribute("displayValues")
	public Iterable<String> addContactTypesDisplayValuesToModel(Model model) {
		
		List<String> displayValues = new ArrayList<>();
		for (ContactType ct : ContactType.values()) {
			displayValues.add(ct.getDisplayValue());
		}
		
		return displayValues;
		
	}
	
	@GetMapping
	public String getProfilePage(@PathVariable("id") String creditId, Model model) {
		
		Credit credit = creditService.getCreditById(creditId);
		model.addAttribute("profile", Optional.ofNullable(credit.getProfile()).orElse(new ClientProfile()));
		
		return "profile";

	}
	
}
