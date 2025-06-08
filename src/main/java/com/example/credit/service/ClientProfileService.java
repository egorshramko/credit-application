package com.example.credit.service;

import com.example.credit.check.data.CheckResult;
import com.example.credit.data.ClientProfile;
import com.example.credit.web.api.dto.profile.ClientProfileDto;

public interface ClientProfileService {
	ClientProfile updateProfile(ClientProfile profile);
	Iterable<CheckResult> checkProfile(ClientProfile profile);
}
