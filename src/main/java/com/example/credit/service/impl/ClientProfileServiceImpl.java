package com.example.credit.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.credit.data.ClientProfile;
import com.example.credit.data.Passport;
import com.example.credit.service.ClientProfileService;
import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.web.api.mapper.ClientProfileMapper;
import com.example.credit.data.repository.ClientProfileRepository;
import com.example.credit.storage.service.TempStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientProfileServiceImpl implements ClientProfileService {

	@Autowired
	private ClientProfileRepository clientProfileRepository;
	
	@Autowired
	private TempStorageService tempStorage;
	
	@Autowired
	private ClientProfileMapper mapper;
	
	@Autowired
	private EntityManager jpaEntityManager;
	
	/*
	 * Метод обновления данных анкеты в соответствии с пришедшей от клиента формой
	 * @param profile - анкета, которую необходимо обновить
	 * @param profileDto - данные пришедшей формы
	 */
	@Override
	public ClientProfile updateProfile(ClientProfile profile, ClientProfileDto profileDto) {
		
		//объект анкеты, готовый к сохранению
		ClientProfile mappedProfile = mapper.toClientProfile(profileDto);
		
		this.updateProfileData(profile, mappedProfile);
		
		return null;
	}
	
	private void updateProfileData(ClientProfile existProfile, ClientProfile otherProfile) {
		
		existProfile.setLastname(otherProfile.getLastname());
		existProfile.setFirstname(otherProfile.getFirstname());
		existProfile.setMiddlename(otherProfile.getMiddlename());
		existProfile.setBirthdate(otherProfile.getBirthdate());
		existProfile.setCitizenship(otherProfile.getCitizenship());
		existProfile.setSex(otherProfile.getSex());
		
		
		
		Passport existPassport = existProfile.getPassport();
		existPassport.setSeries(otherProfile.getPassport().getSeries());
		existPassport.setNumber(otherProfile.getPassport().getNumber());
		existPassport.setIssueDate(otherProfile.getPassport().getIssueDate());
		existPassport.setDepartmentCode(otherProfile.getPassport().getDepartmentCode());
		existPassport.setIssuePlace(otherProfile.getPassport().getIssuePlace());
		
	}
	
	

}
