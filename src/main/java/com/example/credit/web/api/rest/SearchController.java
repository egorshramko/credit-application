package com.example.credit.web.api.rest;

import com.example.credit.data.Client;
import com.example.credit.service.ClientService;
import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/search", produces = "application/json")
public class SearchController {

	@Autowired
	private ClientService clientService;

	@GetMapping(path = "/clients", params = { "lastname", "firstname", "middlename", "birthdate", "passportSeries",
			"passportNumber" })
	public Iterable<Client> getClientsByFilter(@ModelAttribute ClientDto searchRequest) {

		return clientService.searchClients(searchRequest);

	}

}
