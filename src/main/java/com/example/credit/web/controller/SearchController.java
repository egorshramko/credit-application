package com.example.credit.web.controller;

import com.example.credit.data.Client;
import com.example.credit.data.repository.ClientRepository;
import com.example.credit.web.api.dto.ClientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(path="/api/clients/search",
                produces = "application/json")
public class SearchController {

    private final ClientRepository clientRepository;

    public SearchController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(params="all")
    public Iterable<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @GetMapping(params={"lastname", "firstname", "middlename",
                        "birthdate", "passportSeries",
                        "passportNumber"})
    public Iterable<Client> getClientsByFilter(@ModelAttribute ClientDTO searchRequest) {

        return clientRepository.getClientsByFilter(searchRequest.getLastname(),
                searchRequest.getFirstname(),
                searchRequest.getMiddlename(),
                searchRequest.getBirthdate(),
                searchRequest.getPassportSeries(),
                searchRequest.getPassportNumber());

    }


}
