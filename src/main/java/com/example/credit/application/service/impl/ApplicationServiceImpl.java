package com.example.credit.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.credit.application.data.Application;
import com.example.credit.application.repository.ApplicationRepository;
import com.example.credit.application.service.ApplicationService;
import com.example.credit.data.Credit;
import com.example.credit.service.CreditService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private CreditService creditService;
	
	@Autowired
	private ApplicationRepository appRepository;
	
	@Override
	public Application getApplicationByCreditId(String creditId) {
		
		//получение объекта кредита
		Credit credit = creditService.getCreditById(creditId);
		
		//если по кредиту уже существует заявка, то возвращаем эту заявку
		if (credit.getApplication() != null) {
			return credit.getApplication();
		}
		//иначе создаем заявку и возвращаем новую
		else {
			Application app = appRepository.save(new Application());
			credit.setApplication(app);
			creditService.saveCredit(credit);
			return app;
		}
		
	}

	@Override
	public Application saveApplication(String creditId, Application app) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Application rollbackApplication(String creditId) {
		// TODO Auto-generated method stub
		return null;
	}

}
