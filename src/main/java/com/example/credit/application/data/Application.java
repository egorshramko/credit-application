package com.example.credit.application.data;

import java.time.LocalDateTime;

import com.example.credit.application.data.enums.TermUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {
	
	@Id
	@SequenceGenerator(name = "application_seq", sequenceName = "application_pkey_sequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_seq")
	private Long id;
	
	private final LocalDateTime createdDate = LocalDateTime.now();
	
	private Integer amount;
	
	@Enumerated(EnumType.STRING)
	private TermUnit termUnit;
	private Integer termValue;
	private String purpose;
	
	@ManyToOne
	private LoanProduct product;
	private String comment;
	
}
