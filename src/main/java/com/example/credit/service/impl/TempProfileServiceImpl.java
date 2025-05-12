package com.example.credit.service.impl;

import com.example.credit.data.BinaryContent;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Passport;
import com.example.credit.data.PassportScan;
import com.example.credit.service.TempProfileService;
import com.example.credit.storage.service.TempStorageService;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TempProfileServiceImpl implements TempProfileService {

	@Autowired
	private TempStorageService tempStorageService;
	
	@Override
	public ClientProfile addPassportScan(ClientProfile profile, 
			UUID scanUUID) {
		
		Passport passport = profile.getPassport();
		passport.addScan(PassportScan.builder()
				.scanFile(BinaryContent.builder()
						.name(tempStorageService.getFileNameById(scanUUID))
						.uuid(scanUUID)
						.build())
				.build());
		
		return profile;
		
	}
	
	@Override
	public ClientProfile deletePassportScan(ClientProfile profile, 
			UUID scanUUID) {
		
		Passport passport = profile.getPassport();
		passport.removeScan(scanUUID);
		
		return profile;
		
	}
	 
	@Override
	public boolean isPassportScanIdValid(ClientProfile profile,
			UUID scanUUID) {
		
		Passport passport = profile.getPassport();
		PassportScan passportScan = passport.getScans()
			.stream()
			.filter(scan -> {
				BinaryContent scanFile = scan.getScanFile();
				if (scanFile != null) {
					return scanFile.getUuid().equals(scanUUID);
				}
				
				return false;
			})
			.findFirst()
			.orElse(null);
		
		return passportScan != null;
	}
	
	
}
