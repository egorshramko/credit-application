package com.example.credit.service;

import com.example.credit.data.ClientProfile;

import java.util.UUID;

public interface TempProfileService {
	ClientProfile addPassportScan(ClientProfile profile, UUID scanUUID);
}
