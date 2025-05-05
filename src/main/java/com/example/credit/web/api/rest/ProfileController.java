package com.example.credit.web.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.credit.web.api.dto.profile.ClientProfileDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/api/credit/{id}/profile", produces = "application/json")
public class ProfileController {

	@PostMapping("/verificate")
	@ResponseBody
	public ResponseEntity<String> updateProfileData(
		@PathVariable("id") String creditId,
		@RequestBody ClientProfileDto profileDto
	) {
		
		
		
		return null;
	}
	
}
