package com.example.credit.service;

import com.example.credit.data.Client;
import com.example.credit.web.api.dto.ClientDto;
import com.example.credit.data.ClientProfile;

import java.util.Optional;

public interface ClientService {

	Iterable<Client> searchClients(ClientDto clientDto);

	Client createClient(ClientDto clientDto);
	
	Client updateClient(ClientProfile clientProfile);

	Optional<Client> findClientById(String id);

}
