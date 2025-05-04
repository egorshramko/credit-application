package com.example.credit.web.api.mapper;

import com.example.credit.data.Contact;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.enums.Sex;
import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.web.api.dto.profile.ContactDto;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientProfileMapper {
	
	@Autowired
	private ContactMapper contactMapper;
	
	@Autowired
	private PassportMapper passportMapper;
	
	@Autowired
	private BinaryContentMapper binaryContentMapper;
	
	public ClientProfile toClientProfile(ClientProfileDto profileDto) {
		
		List<Contact> contactsList = new ArrayList<>();
		
		if (profileDto.getContacts() != null && profileDto.getContacts().size() > 0) {
			for (ContactDto contactDto : profileDto.getContacts()) {
				contactsList.add(
						contactMapper.toContact(contactDto)
					);
			}
		}
		
		
		return ClientProfile.builder()
				.lastname(profileDto.getLastname())
				.firstname(profileDto.getFirstname())
				.middlename(profileDto.getMiddlename())
				.birthdate(LocalDate.parse(profileDto.getBirthdate()))
				.citizenship(profileDto.getCitizenship())
				.sex(Sex.valueOf(profileDto.getSex()))
				.photo(binaryContentMapper.toBinaryContent(profileDto.getPhoto()))
				.passport(passportMapper.toPassport(profileDto.getPassport()))
				.contacts(contactsList)
				.tin(profileDto.getTin())
				.comment(profileDto.getComment())
				.consentPersonalData(profileDto.getConsentPersonalData())
				.build();
	}
	
}
