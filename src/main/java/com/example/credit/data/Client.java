package com.example.credit.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

@Slf4j
@Data
@AllArgsConstructor
public class Client {

    private String id;
    private String lastname;
    private String firstname;
    private String middlename;
    private LocalDate birthdate;
    private String passportSeries;
    private String passportNumber;

}
