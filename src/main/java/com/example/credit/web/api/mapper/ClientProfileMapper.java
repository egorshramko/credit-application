package com.example.credit.web.api.mapper;

import com.example.credit.data.ClientProfile;
import com.example.credit.data.Contact;
import com.example.credit.data.enums.ContactType;
import com.example.credit.data.enums.Sex;
import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.web.api.dto.profile.ContactDto;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ClientProfileMapper {
	
	@Autowired
	private PassportMapper passportMapper;
	
	@Autowired
	private BinaryContentMapper binaryContentMapper;
	
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
	
	public ClientProfile createProfileFromDto(ClientProfileDto profileDto) {
		return ClientProfile.builder()
				.build();
	}
	
	public ClientProfile updateProfileFromDto(ClientProfile profile, ClientProfileDto profileDto) {
		
		profile.setLastname(profileDto.getLastname());
		profile.setFirstname(profileDto.getFirstname());
		profile.setMiddlename(profileDto.getMiddlename());
		profile.setBirthdate(LocalDate.parse(profileDto.getBirthdate(), 
											DateTimeFormatter.ISO_LOCAL_DATE));
		profile.setTin(profileDto.getTin());
		profile.setCitizenship(profileDto.getCitizenship());
		profile.setSex(Sex.valueOf(profileDto.getSex()));
		profile.setConsentPersonalData(profileDto.getConsentPersonalData());
		profile.setComment(profileDto.getComment());
		
		profile.setPassport(
				passportMapper.updatePassportFromDto(
						profile.getPassport(), 
						profileDto.getPassport()));
		
		//обновление существующих контактов
		for (Contact contact : profile.getContacts()) {
			ContactDto contactDto = profileDto.getContacts()
					.stream()
					.filter(cdto -> cdto.getUuid().equals(contact.getUuid().toString()))
					.findFirst()
					.orElse(null);
			
			if (contactDto != null) {
				contact.setContactType(ContactType.valueOf(contactDto.getContactType()));
				contact.setPhoneNumber(contactDto.getPhoneNumber());
				contact.setComment(contactDto.getComment());
			}
			
		}
		
		if (profileDto.getPhoto() != null) {
			profile.setPhoto(
					binaryContentMapper
							.toBinaryContent(profileDto.getPhoto()));
		}
		
		
		
		return profile;
		
	}
	
}
