package com.example.credit.check.checks.impl;

import com.example.credit.check.checks.Check;
import com.example.credit.check.data.CheckResult;
import com.example.credit.data.ClientProfile;

public class PassportCheck implements Check {
	
	private ClientProfile profile;
	
	public PassportCheck(ClientProfile profile) {
		this.profile = profile;
	}
	
	@Override
	public CheckResult check() {
		return new CheckResult();
	}
	
}
