package com.example.credit.service.impl;

import com.example.credit.data.Client;
import com.example.credit.data.Passport;
import com.example.credit.data.repository.ClientRepository;
import com.example.credit.data.repository.PassportRepository;
import com.example.credit.service.ClientService;
import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PassportRepository passportRepository;

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

    /**
     * Метод создает нового клиента
     * @param clientDto - пришедшие данные нового клиента
     * @return - созданный объект клиента
     */
    @Override
    public Client createClient(ClientDto clientDto) {

        Passport clientPassport = passportRepository.save(
                new Passport(
                        null,
                        clientDto.getPassportSeries(),
                        clientDto.getPassportNumber(),
                        null,
                        null,
                        null
                )
        );

        LocalDate clientBirthdate;
        try {
            clientBirthdate = LocalDate.parse(clientDto.getBirthdate());
        }
        catch (DateTimeParseException err) {
            log.warn("Empty client birthdate");
            clientBirthdate = null;
        }

        return clientRepository.save(
                new Client(null,
                        clientDto.getLastname(),
                        clientDto.getFirstname(),
                        clientDto.getMiddlename(),
                        clientBirthdate,
                        null,
                        null,
                        null,
                        clientPassport
                )
        );
    }

    @Override
    public Optional<Client> findClientById(String id) {
        return clientRepository.findById(Long.parseLong(id));
    }

}
