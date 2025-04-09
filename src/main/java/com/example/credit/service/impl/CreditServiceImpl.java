package com.example.credit.service.impl;

import com.example.credit.data.Client;
import com.example.credit.data.ClientProfile;
import com.example.credit.data.Credit;
import com.example.credit.data.enums.CreditStage;
import com.example.credit.data.repository.ClientProfileRepository;
import com.example.credit.data.repository.CreditRepository;
import com.example.credit.service.ClientService;
import com.example.credit.service.CreditService;
import com.example.credit.web.api.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private ClientProfileRepository profileRepository;

    /**
     * Метод создает объект кредита для нового клиента
     * @param clientDto - транспортный объект из запроса с данными нового клиента
     * @return - созданный объект кредита
     */
    @Override
    public Credit createCreditForNewClient(ClientDto clientDto) {

        //создаем нового клиента по пришедшим данным
        Client borrower = clientService.createClient(clientDto);

        //создаем объект кредита
        Credit createdCredit = creditRepository.save(
                Credit.builder()
                        .borrower(borrower)
                        .stage(CreditStage.CREDIT_FORM)
                        .build()
        );

        //создаем анкету с данными клиента
        this.fillCreditProfile(createdCredit);

        log.info("Created credit with id: " + createdCredit.getId());

        return createdCredit;
    }

    @Override
    public Credit createCreditForExistClient(String clientId) throws RuntimeException {


        Optional<Client> borrowerContainer = clientService.findClientById(clientId);
        if (borrowerContainer.isPresent()) {
            Client borrower = borrowerContainer.get();

            Credit createdCredit = creditRepository.save(
                    Credit.builder()
                            .borrower(borrower)
                            .stage(CreditStage.CREDIT_FORM)
                            .build()
            );

            //создаем анкету с данными клиента
            this.fillCreditProfile(createdCredit);

            log.info("Created credit with id: " + createdCredit.getId());

            return createdCredit;
        }
        else {
            throw new RuntimeException("Desired client not found");
        }

    }

    @Override
    public Credit getCreditById(String creditId) {

        Optional<Credit> desiredCreditOrNull = creditRepository.findById(Long.parseLong(creditId));

        //fillCreditProfile вызывается для дополнительной проверки наличия анкеты у кредита
        return desiredCreditOrNull.map(this::fillCreditProfile).orElse(null);

    }

    @Override
    public Iterable<Credit> getActiveCredits() {

        return creditRepository.getActiveCredits();

    }

    @Override
    public Credit fillCreditProfile(Credit credit) {

        Client borrower = credit.getBorrower();
        if (credit.getProfile() == null && borrower != null) {

            //создаем анкету с данными найденного клиента
            ClientProfile profile = profileRepository.save(ClientProfile.builder()
                    .lastname(borrower.getLastname())
                    .firstname(borrower.getFirstname())
                    .middlename(borrower.getMiddlename())
                    .birthdate(borrower.getBirthdate())
                    .passport(borrower.getPassport())
                    .citizenship(borrower.getCitizenship())
                    .sex(borrower.getSex())
                    .tin(borrower.getTin())
                    //.contacts(borrower.getContacts())
                    .build()
            );

            credit.setProfile(profile);

            return creditRepository.save(credit);
        }

        return credit;
    }

}
