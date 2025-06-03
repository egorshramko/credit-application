package com.example.credit.check.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.credit.check.checks.factory.impl.ProfileCheckFactory;
import com.example.credit.check.data.CheckResult;
import com.example.credit.check.executor.CheckExecutor;
import com.example.credit.check.service.CheckService;
import com.example.credit.data.ClientProfile;

@Service
public class CheckServiceImpl implements CheckService {
	
	@Autowired
	private CheckExecutor checkExecutor;
	
	@Override
	public Iterable<CheckResult> checkProfile(ClientProfile profile) {
		
		ProfileCheckFactory profileCheckFactory = new ProfileCheckFactory(profile);
		
		return checkExecutor.executeChecks(profileCheckFactory);
	}
	
}
