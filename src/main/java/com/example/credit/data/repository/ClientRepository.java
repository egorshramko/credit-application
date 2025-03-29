package com.example.credit.data.repository;

import com.example.credit.data.Client;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class ClientRepository {

    List<Client> clients = new ArrayList<>();

    public ClientRepository() {

        clients.add(new Client("1", "Иванов", "Иван", "Иванович",
                LocalDate.of(1990, Month.DECEMBER, 15),
                "50 18", "123312"));
        clients.add(new Client("2", "Петров", "Петр", "Петрович",
                LocalDate.of(1999, Month.OCTOBER, 1), "01 01", "385476"));

    }

    public Iterable<Client> getAllClients() {
        return clients;
    }

    public Iterable<Client> getClientsByFilter(String lastname, String firstname,
                                               String middlename, String birthdate,
                                               String passportSeries,
                                               String passportNumber) {

        Stream<Client> clientStream = clients.stream()
                .filter(client -> lastname == null ||
                        lastname.equals("") || client.getLastname().equals(lastname))
                .filter(client -> firstname == null ||
                        firstname.equals("") || client.getFirstname().equals(firstname))
                .filter(client -> middlename == null ||
                        middlename.equals("") || client.getMiddlename().equals(middlename))
                .filter(client -> birthdate == null ||
                        birthdate.equals("") || client.getBirthdate().equals(LocalDate.parse(birthdate)))
                .filter(client -> passportSeries == null ||
                        passportSeries.equals("") || client.getPassportSeries().equals(passportSeries))
                .filter(client -> passportNumber == null ||
                        passportNumber.equals("") || client.getPassportNumber().equals(passportNumber));

        return clientStream.toList();

    }

}
