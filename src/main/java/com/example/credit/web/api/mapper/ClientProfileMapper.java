package com.example.credit.web.api.mapper;

import com.example.credit.data.ClientProfile;
import com.example.credit.storage.service.TempStorageService;
import com.example.credit.web.api.dto.profile.ClientProfileDto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientProfileMapper {
	
	@Autowired
	private TempStorageService tempStorage;
	
	public ClientProfileDto toDTO(ClientProfile profile) {
		
		//если в анкете содержится фотография, то ее необходимо 
		
		return ClientProfileDto.builder()
				.id(Optional.ofNullable(profile.getId()).orElse(null).toString())
				.lastname(profile.getLastname())
				.firstname(profile.getFirstname())
				.middlename(profile.getMiddlename())
				.birthdate(Optional.ofNullable(profile.getBirthdate()).orElse(null).toString())
				.citizenship(profile.getCitizenship())
				.sex(Optional.ofNullable(profile.getSex()).orElse(null).toString())
				.tin(profile.getTin())
				.comment(profile.getComment())
				.consentPersonalData(profile.getConsentPersonalData())
				.build();
	}
	
}
