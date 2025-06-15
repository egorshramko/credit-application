package com.example.credit.application.data;

import java.time.LocalDateTime;

import com.example.credit.application.data.enums.TermUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Application {
	
	@Id
	private Long id;
	
	private LocalDateTime createdDate = LocalDateTime.now();
	
	private Integer amount;
	private TermUnit termUnit;
	private String purpose;
	
	@ManyToOne
	private LoanProduct product;
	private String comment;
	
}
