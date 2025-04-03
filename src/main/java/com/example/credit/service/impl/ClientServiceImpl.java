package com.example.credit.service.impl;

import com.example.credit.data.Client;
import com.example.credit.data.repository.ClientRepository;
import com.example.credit.service.ClientService;
import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Iterable<Client> searchClients(ClientDto clientDto) {

        LocalDate requestBirthdate;
        try {
            requestBirthdate = LocalDate.parse(clientDto.getBirthdate());
        }
        catch (DateTimeException err) {
            requestBirthdate = null;
        }

        return clientRepository.getClientsBySearchFilter(
                clientDto.getLastname(),
                clientDto.getFirstname(),
                clientDto.getMiddlename(),
                requestBirthdate,
                clientDto.getPassportSeries(),
                clientDto.getPassportNumber());

    }

}
