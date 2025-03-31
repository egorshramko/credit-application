package com.example.credit.data.repository;

import com.example.credit.data.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> getClientsByLastnameAndFirstname(String lastname, String firstname);

    @Query("select cl from Client cl " +
            "left join cl.passport pas " +
            "where " +
            "(:lastname is null or :lastname = '' or cl.lastname = :lastname) and " +
            "(:firstname is null or :firstname = '' or cl.firstname = :firstname) and " +
            "(:middlename is null or :middlename = '' or cl.middlename = :middlename) and " +
            "(cast(:birthdate as date) is null or " +
            "cast(cl.birthdate as date) = cast(:birthdate as date)) and " +
            "(:passport_series is null or :passport_series = '' or pas.series = :passport_series) and " +
            "(:passport_number is null or :passport_number = '' or pas.number = :passport_number)"
    )
    List<Client> getClientsBySearchFilter(
            @Param("lastname") String lastname,
            @Param("firstname") String firstname,
            @Param("middlename") String middlename,
            @Param("birthdate") LocalDate birthdate,
            @Param("passport_series") String passportSeries,
            @Param("passport_number") String passportNumber);

}
