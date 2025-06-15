package com.example.credit.application.repository;

import com.example.credit.application.data.LoanProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {

}
