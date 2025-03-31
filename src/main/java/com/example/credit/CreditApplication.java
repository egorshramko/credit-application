package com.example.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.credit.web", "com.example.credit.data.repository"})
@EntityScan("com.example.credit.data")
@EnableJpaRepositories("com.example.credit.data.repository")
public class CreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditApplication.class, args);
	}

}
