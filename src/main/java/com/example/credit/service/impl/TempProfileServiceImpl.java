package com.example.credit.service.impl;

import com.example.credit.data.BinaryContent;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Passport;
import com.example.credit.data.PassportScan;
import com.example.credit.service.TempProfileService;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TempProfileServiceImpl implements TempProfileService {

	@Override
	public ClientProfile addPassportScan(ClientProfile profile, 
			UUID scanUUID) {
		
		Passport passport = profile.getPassport();
		passport.addScan(PassportScan.builder()
				.scanFile(BinaryContent.builder()
						.uuid(scanUUID)
						.build())
				.build());
		
		return profile;
		
	}
	
	
}
