package com.example.credit.service.impl;

import com.example.credit.data.Client;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Contact;
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
import java.util.ArrayList;
import java.util.List;
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
		} catch (DateTimeException err) {
			requestBirthdate = null;
		}
		
		log.info("middlename: " + clientDto.getMiddlename());
		
		List<Client> searchedClients = clientRepository.getClientsBySearchFilter(clientDto.getLastname(), clientDto.getFirstname(),
				clientDto.getMiddlename(), requestBirthdate, clientDto.getPassportSeries(),
				clientDto.getPassportNumber());

		log.info("founded clients: " + searchedClients.size());
		
		//маппинг необходимых значений для возврата ответа
		List<Client> returnedClientsInfo = new ArrayList<>();
		
		for (Client client : searchedClients) {
			returnedClientsInfo.add(Client.builder()
										.id(client.getId())
										.lastname(client.getLastname())
										.firstname(client.getFirstname())
										.middlename(client.getMiddlename())
										.passport(Passport.builder()
													.series(client.getPassport().getSeries())
													.number(client.getPassport().getNumber())
													.build()
												)
										.build()
									);
		}
		
		return returnedClientsInfo;

	}

	/**
	 * Метод создает нового клиента
	 * 
	 * @param clientDto - пришедшие данные нового клиента
	 * @return - созданный объект клиента
	 */
	@Override
	public Client createClient(ClientDto clientDto) {

		Passport clientPassport = passportRepository.save(
				Passport.builder().series(clientDto.getPassportSeries()).number(clientDto.getPassportNumber()).build());

		LocalDate clientBirthdate;
		try {
			clientBirthdate = LocalDate.parse(clientDto.getBirthdate());
		} catch (DateTimeParseException err) {
			log.warn("Empty client birthdate");
			clientBirthdate = null;
		}

		return clientRepository.save(Client.builder().lastname(clientDto.getLastname())
				.firstname(clientDto.getFirstname()).middlename(clientDto.getMiddlename()).birthdate(clientBirthdate)
				.passport(clientPassport).build());
	}

	@Override
	public Optional<Client> findClientById(String id) {
		return clientRepository.findById(Long.parseLong(id));
	}
	
	@Override
	public Client updateClientFromProfile(Client client, ClientProfile clientProfile) {
		
		client.setLastname(clientProfile.getLastname());
		client.setFirstname(clientProfile.getFirstname());
		client.setMiddlename(clientProfile.getMiddlename());
		
		client.setBirthdate(clientProfile.getBirthdate());
		client.setCitizenship(clientProfile.getCitizenship());
		
		client.setSex(clientProfile.getSex());
		client.setPassport(clientProfile.getPassport());
		
		client.setTin(clientProfile.getTin());
		
		List<Contact> clientContacts = new ArrayList<>();
		for (Contact contact : clientProfile.getContacts()) {
			clientContacts.add(Contact.builder()
					.uuid(contact.getUuid())
					.contactType(contact.getContactType())
					.phoneNumber(contact.getPhoneNumber())
					.comment(contact.getComment())
					.build());
		}
		client.setContacts(clientContacts);
		client.setPhoto(clientProfile.getPhoto());
		
		return clientRepository.save(client);
	}

}
