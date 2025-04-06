package com.example.credit.data.repository;

import com.example.credit.data.Credit;
import com.example.credit.web.api.dto.ClientDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {

    @Query("Select cr from Credit cr " +
            "left join cr.borrower cl " +
            "where " +
            "cr.stage != 'COMPLETED'")
    Iterable<Credit> getActiveCredits();

}
