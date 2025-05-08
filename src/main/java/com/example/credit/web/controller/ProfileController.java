package com.example.credit.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.credit.data.BinaryContent;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Contact;
import com.example.credit.data.Credit;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.Sex;
import com.example.credit.service.CreditService;
import com.example.credit.storage.service.TempStorageService;
import com.example.credit.web.api.dto.profile.ContactDto;

import jakarta.json.Json;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/credit/{id}/profile")
@SessionAttributes("profile")
public class ProfileController {

	@Autowired
	private CreditService creditService;
	
	@Autowired
	private TempStorageService tempStorageService;
	
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
	
	@PostMapping(path = "/addContact")
	@ResponseBody
	public ResponseEntity<String> addContactToProfile(HttpSession session) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			ClientProfile profile = (ClientProfile) profileObj;
			
			UUID newContactUUID = UUID.randomUUID();
			
			profile.addContact(Contact.builder()
								.uuid(newContactUUID)
								.build());
			
			session.setAttribute("profile", profile);
			
			return ResponseEntity.ok()
					.body(Json.createObjectBuilder()
							.add("id", newContactUUID.toString())
							.build()
							.toString());
		}
		
		return ResponseEntity.internalServerError()
				.build();
	}
	
	@PostMapping(path = "/removeContact")
	@ResponseBody
	public ResponseEntity<String> removeContactFromProfile(HttpSession session, @RequestBody ContactDto contactDto) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			ClientProfile profile = (ClientProfile) profileObj;
			
			log.info("profile id: " + profile.getId().toString());
			log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
			
			UUID removedContactUUID = UUID.fromString(contactDto.getUuid());
			if (profile.removeContact(removedContactUUID)) {
				
				log.info("contact removed");
				log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
				
				return ResponseEntity.ok()
						.build();
			}
			
			log.warn("incorrect contact UUID!");
			log.info("contacts.size: " + Integer.toString(profile.getContacts().size()));
			return ResponseEntity.notFound()
					.build();
		}
		
		return ResponseEntity.internalServerError()
				.build();
	}
	
	@PostMapping("/addPhoto")
	@ResponseBody
	public ResponseEntity<?> addPhotoToProfile(HttpSession session, @RequestParam(value = "file", required = false) @Valid final MultipartFile file) {
		
		Object profileObj = session.getAttribute("profile");
		
		if (profileObj instanceof ClientProfile) {
			
			ClientProfile profile = (ClientProfile) profileObj;
			
			try {
				UUID photoUUID = tempStorageService.upload(file);
				profile.setPhoto(BinaryContent.builder()
									.uuid(photoUUID)
									.build());
				
				return ResponseEntity.ok(Json.createObjectBuilder()
											.add("id", photoUUID.toString())
											.build());
				
			}
			catch (IOException exception) {
				return ResponseEntity.badRequest().build();
			}
			
		}
		
		return ResponseEntity.internalServerError()
				.build();
	}
	
	
}
