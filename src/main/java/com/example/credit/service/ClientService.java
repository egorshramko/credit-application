package com.example.credit.service;

import com.example.credit.data.Client;
import com.example.credit.web.api.dto.ClientDto;

import java.util.Optional;

public interface ClientService {

    Iterable<Client> searchClients(ClientDto clientDto);

    Client createClient(ClientDto clientDto);

    Optional<Client> findClientById(String id);

}
