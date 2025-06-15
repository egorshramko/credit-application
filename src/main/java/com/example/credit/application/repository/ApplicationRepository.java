package com.example.credit.application.repository;

import com.example.credit.application.data.Application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
