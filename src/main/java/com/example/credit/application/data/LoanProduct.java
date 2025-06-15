package com.example.credit.application.data;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
	
	@Column(unique = true)
	@NotNull
	private String name;
	
	@NotNull
	private Double rate;
	
	@NotNull
	private Integer minTerm;
	
	@NotNull
	private Integer maxTerm;
	
	@NotNull
	private Integer minAmount;
	
	@NotNull
	private Integer maxAmount;
	
}
