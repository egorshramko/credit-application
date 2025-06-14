package com.example.credit.service;

import com.example.credit.data.Credit;
import com.example.credit.web.api.dto.ClientDto;
import com.example.credit.data.Client;
import com.example.credit.data.ClientProfile;

public interface CreditService {

	Credit createCreditForNewClient(ClientDto clientDto);

	Credit createCreditForExistClient(String clientId);

	Credit getCreditById(String creditId);
	
	ClientProfile getProfileByCreditId(Long creditId);
	
	Client getClientByCreditId(Long creditId);

	Iterable<Credit> getActiveCredits();

	Credit fillCreditProfile(Credit credit);
	
	Credit moveToNextStage(Credit credit);

}
