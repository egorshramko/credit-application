package com.example.credit.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

    private String id;
    private String lastname;
    private String firstname;
    private String middlename;
    private String birthdate;
    private String passportSeries;
    private String passportNumber;

}
