package com.example.credit.data;

import lombok.Data;

import java.util.Date;

@Data
public class Client {

    private final String id;
    private final String lastname;
    private final String firstname;
    private final String middlename;
    private final Date birthdate;
    private final String passport_series;
    private final String passport_number;

}
