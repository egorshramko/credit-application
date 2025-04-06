package com.example.credit.data.repository;

import com.example.credit.data.Passport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends CrudRepository<Passport, Long> {
}
