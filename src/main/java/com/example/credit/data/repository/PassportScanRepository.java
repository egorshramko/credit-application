package com.example.credit.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.credit.data.PassportScan;

@Repository
public interface PassportScanRepository extends CrudRepository<PassportScan, Long> {

}
