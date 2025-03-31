package com.example.credit.web.controller;

import com.example.credit.data.Client;
import com.example.credit.data.repository.ClientRepository;
import com.example.credit.web.api.dto.ClientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;


@Slf4j
@RestController
@RequestMapping(path="/api/clients/search",
                produces = "application/json")
public class SearchController {

    ClientRepository clientRepository;

    public SearchController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(params="all")
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping(params={"lastname", "firstname", "middlename",
                        "birthdate", "passportSeries",
                        "passportNumber"})
    public Iterable<Client> getClientsByFilter(@ModelAttribute ClientDTO searchRequest) {

        LocalDate requestBirthdate;
        try {
            requestBirthdate = LocalDate.parse(searchRequest.getBirthdate());
        }
        catch (DateTimeException err) {
            requestBirthdate = null;
        }

        return clientRepository.getClientsBySearchFilter(
                searchRequest.getLastname(),
                searchRequest.getFirstname(),
                searchRequest.getMiddlename(),
                requestBirthdate,
                searchRequest.getPassportSeries(),
                searchRequest.getPassportNumber());

    }


}
