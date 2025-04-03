package com.example.credit.service;

import com.example.credit.data.Client;
import com.example.credit.web.api.dto.ClientDto;

public interface ClientService {

    Iterable<Client> searchClients(ClientDto clientDto);

}
