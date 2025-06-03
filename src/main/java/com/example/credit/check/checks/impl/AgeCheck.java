package com.example.credit.check.checks.impl;

import com.example.credit.check.checks.Check;
import com.example.credit.check.data.CheckResult;
import com.example.credit.data.ClientProfile;

public class AgeCheck implements Check {

	private ClientProfile profile;
	
	public AgeCheck(ClientProfile profile) {
		this.profile = profile;
	}
	
	
	@Override
	public CheckResult check() {
		
		return null;
	}

}
