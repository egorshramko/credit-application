package com.example.credit.check.checks.impl;

import java.time.LocalDate;
import java.time.Period;

import com.example.credit.check.checks.Check;
import com.example.credit.check.data.CheckResult;
import com.example.credit.check.data.enums.ResultType;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Passport;

public class PassportCheck implements Check {
	
	private ClientProfile profile;
	
	public PassportCheck(ClientProfile profile) {
		this.profile = profile;
	}
	
	@Override
	public CheckResult check() {
		
		Passport passport = profile.getPassport();
		LocalDate issueDate = passport.getIssueDate();
		
		LocalDate birthDate = profile.getBirthdate();
		
		//вычисление возраста клиента
		int age = Period.between(birthDate, LocalDate.now())
							.getYears();
		
		CheckResult checkResult = new CheckResult();
		checkResult.setCheckName("Passport check");
		
		LocalDate correctStartDate;
		LocalDate correctEndDate;
		
		if (age >= 14 && age < 20) {
			
			correctStartDate = birthDate.plusYears(14);
			correctEndDate = birthDate.plusYears(20);
			
		}
		else if (age >= 20 && age < 45) {
			correctStartDate = birthDate.plusYears(20);
			correctEndDate = birthDate.plusYears(45);
		}
		else if (age > 45) {
			correctStartDate = birthDate.plusYears(45);
			correctEndDate = null;
			
		}
		else {
			checkResult.setResultType(ResultType.REJECTED);
			checkResult.setMessage("Client under 14 years old");
			return checkResult;
		}
		
		if (issueDate.isAfter(correctStartDate)) {
			
			if (correctEndDate != null && issueDate.isBefore(correctEndDate) || 
					correctEndDate == null) {
				
				checkResult.setResultType(ResultType.APPROVED);
				
			}
			else {
				throw new RuntimeException("Incorrect calculation of correctEndDate variable");
			}
			
		}
		else {
			checkResult.setResultType(ResultType.REJECTED);
			checkResult.setMessage("Passport is expired");
		}
		
		return checkResult;
	}
	
}
