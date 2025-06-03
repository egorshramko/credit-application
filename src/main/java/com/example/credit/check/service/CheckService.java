package com.example.credit.check.service;

import com.example.credit.check.data.CheckResult;
import com.example.credit.data.ClientProfile;

public interface CheckService {
	
	Iterable<CheckResult> checkProfile(ClientProfile profile);
	
}
