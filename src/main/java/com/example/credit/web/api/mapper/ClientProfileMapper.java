package com.example.credit.web.api.mapper;

import com.example.credit.data.ClientProfile;
import com.example.credit.web.api.dto.profile.ClientProfileDto;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ClientProfileMapper {
	
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
