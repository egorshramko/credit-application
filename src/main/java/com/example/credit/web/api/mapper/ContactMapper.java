package com.example.credit.web.api.mapper;

import com.example.credit.data.Contact;
import com.example.credit.data.enums.ContactType;
import com.example.credit.web.api.dto.profile.ContactDto;

import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

	public Contact toContact(ContactDto contactDto) {
		return Contact.builder()
				.contactType(ContactType.valueOf(contactDto.getContactType()))
				.phoneNumber(contactDto.getPhoneNumber())
				.comment(contactDto.getComment())
				.build();
	}
	
}
