package com.example.credit.web.api.dto.profile;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class ClientProfileDto {
	
	private Long id;
	private String lastname;
	private String firstname;
	private String middlename;
	private String birthdate;
	private String citizenship;
	private String sex;
	
	private String photo;
	
	private PassportDto passport;
	
	private final List<ContactDto> contacts = new ArrayList<>();
	
	private String tin;
	private String comment;
	private Boolean consentPersonalData;
}
