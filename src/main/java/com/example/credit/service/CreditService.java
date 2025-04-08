package com.example.credit.service;

import com.example.credit.data.Credit;
import com.example.credit.web.api.dto.ClientDto;

public interface CreditService {

    Credit createCreditForNewClient(ClientDto clientDto);
    Credit createCreditForExistClient(String clientId);

    Credit getCreditById(String creditId);

    Iterable<Credit> getActiveCredits();

    Credit fillCreditProfile(Credit credit);

}
