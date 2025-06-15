package com.example.credit.application.service;

import com.example.credit.application.data.Application;

public interface ApplicationService {
	Application getApplicationByCreditId(String creditId);
	Application saveApplication(String creditId, Application app);
	Application rollbackApplication(String creditId);
}
