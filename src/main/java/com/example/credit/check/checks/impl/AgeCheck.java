package com.example.credit.check.checks.impl;

import com.example.credit.check.checks.Check;
import com.example.credit.check.data.CheckResult;
import com.example.credit.check.data.enums.ResultType;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.enums.Sex;

import java.time.LocalDate;
import java.time.Period;

public class AgeCheck implements Check {

	private ClientProfile profile;
	
	public AgeCheck(ClientProfile profile) {
		this.profile = profile;
	}
	
	
	@Override
	public CheckResult check() {
		
		LocalDate birthDate = profile.getBirthdate();
		Sex sex = profile.getSex();
		
		int age = Period.between(birthDate, LocalDate.now())
				.getYears();
		
		CheckResult checkResult = new CheckResult();
		checkResult.setCheckName("Age check");
		
		switch (sex) {
		case MALE:
			if (age >= 18 && age < 65) {
				checkResult.setResultType(ResultType.APPROVED);
			}
			else {
				checkResult.setResultType(ResultType.REJECTED);
			}
			break;
		case FEMALE:
			if (age >= 18 && age < 60) {
				checkResult.setResultType(ResultType.APPROVED);
			}
			else {
				checkResult.setResultType(ResultType.REJECTED);
			}
			break;
		
		}
		
		if (checkResult.getResultType() == ResultType.REJECTED) {
			if (age < 18) {
				checkResult.setMessage("Underage client");
			}
			else {
				checkResult.setMessage("Retirement age client");
			}
		}
		
		return checkResult;
	}

}
