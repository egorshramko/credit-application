package com.example.credit.application.service.impl;

import org.springframework.stereotype.Service;

import com.example.credit.application.data.Application;
import com.example.credit.application.service.ApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Override
	public Application getApplicationByCreditId(String creditId) {
		// TODO Auto-generated method stub
		return null;
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
