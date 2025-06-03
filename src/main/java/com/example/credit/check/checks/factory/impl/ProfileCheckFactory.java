package com.example.credit.check.checks.factory.impl;

import com.example.credit.check.checks.Check;
import com.example.credit.check.checks.factory.CheckFactory;
import com.example.credit.data.ClientProfile;
import com.example.credit.check.checks.impl.PassportCheck;
import com.example.credit.check.checks.impl.AgeCheck;

import java.util.List;
import java.util.ArrayList;

public class ProfileCheckFactory implements CheckFactory {

	public ClientProfile checkedProfile;
	
	public ProfileCheckFactory(ClientProfile profile) {
		this.checkedProfile = profile;
	}
	
	@Override
	public Iterable<Check> createChecks() {
		
		List<Check> checks = new ArrayList<>();
		checks.add(new PassportCheck(checkedProfile));
		checks.add(new AgeCheck(checkedProfile));
		
		return checks;
	}

}
