package com.example.credit.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.credit.check.data.CheckResult;
import com.example.credit.check.data.enums.ResultType;
import com.example.credit.check.service.CheckService;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Passport;
import com.example.credit.service.ClientProfileService;
import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.web.api.mapper.ClientProfileMapper;
import com.example.credit.data.repository.ClientProfileRepository;
import com.example.credit.data.repository.PassportRepository;
import com.example.credit.data.repository.PassportScanRepository;
import com.example.credit.storage.service.TempStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientProfileServiceImpl implements ClientProfileService {

	@Autowired
	private ClientProfileRepository clientProfileRepository;
	
	@Autowired
	private PassportRepository passportRepository;
	
	@Autowired
	private PassportScanRepository passportScanRepository;
	
	@Autowired
	private CheckService checkService;
	
	/*
	 * Метод обновления данных анкеты в соответствии с пришедшей от клиента формой
	 * @param profile - анкета, которую необходимо обновить
	 * @param profileDto - данные пришедшей формы
	 */
	@Override
	public ClientProfile updateProfile(ClientProfile profile) {
		
		passportRepository.save(profile.getPassport());
		profile = clientProfileRepository.save(profile);
		
		return profile;
	}
	
	@Override
	public Iterable<CheckResult> checkProfile(ClientProfile clientProfile) {
		Iterable<CheckResult> checkResults = checkService.checkProfile(clientProfile);
		
		//проверка, что все проверки пройдены
		int rejectedChecks = 0;
		for (CheckResult checkResult : checkResults) {
			if (checkResult.getResultType() == ResultType.REJECTED) {
				rejectedChecks++;
			}
		}
		
//		if (rejectedChecks == 0) {
//			this.updateProfile(clientProfile);
//		}
		
		return checkResults;
	}

	
	

}
