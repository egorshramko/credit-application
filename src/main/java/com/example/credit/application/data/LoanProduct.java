package com.example.credit.application.data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoanProduct {
	
	@Id
	private Long id;
	
	private final LocalDateTime createdDate = LocalDateTime.now();
	
	private String name;
	
	private Double rate;
	
	private Integer minTerm;
	private Integer maxTerm;
	
	private Integer minAmount;
	private Integer maxAmount;
	
}
