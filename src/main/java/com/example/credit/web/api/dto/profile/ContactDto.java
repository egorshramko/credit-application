package com.example.credit.web.api.dto.profile;

import lombok.Data;

@Data
public class ContactDto {
	
	private String contactType;
	private String phoneNumber;
	private String comment;
	private String uuid;
	
}
